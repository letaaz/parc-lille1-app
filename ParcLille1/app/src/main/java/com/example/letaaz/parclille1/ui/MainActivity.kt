package com.example.letaaz.parclille1.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.letaaz.parclille1.InjectorUtils
import com.example.letaaz.parclille1.R
import com.example.letaaz.parclille1.SimpleLocationService
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.ui.main.ProblemeViewModel
import com.example.letaaz.parclille1.ui.ui.ProblemeListAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val ADD_PROBLEME_ACTIVITY_REQUEST_CODE = 1
        private const val DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE = 2
        const val LOCATION_PERMISSION_REQUEST_CODE = 3
        val DATE_FORMAT = SimpleDateFormat("dd/MM/yyy à HH:mm")
        val GEOCENTER : LatLng = LatLng(50.616266574185104, 3.1003794048932605)
        private val ZOOM_LEVEL : Float = 6.8f
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mapView : View
    private lateinit var homeView : View
    private lateinit var mProblemeViewModel: ProblemeViewModel
    private lateinit var mSimpleLocationService: SimpleLocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapView = findViewById<View>(R.id.map_view)
        homeView = findViewById<View>(R.id.home_view)

        val recyclerView = findViewById<RecyclerView>(R.id.probleme_recyclerview)
        val adapter = ProblemeListAdapter(this)
        adapter.onItemClick = {
            val intent = Intent(this, DetailProblemeActivity::class.java)
            intent.putExtra("PROB_ID", it.id)
            startActivityForResult(intent, DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val mAjoutButton = findViewById<FloatingActionButton>(R.id.ajout_probleme_fab)

        val factory = InjectorUtils.provideProblemeViewModelFactory(this)
        mProblemeViewModel = ViewModelProviders.of(this, factory).get(ProblemeViewModel::class.java)
        mProblemeViewModel.mProblemes.observe(this, Observer {
            adapter.setProblemes(it!!)
            updateProblemesMarker(it, mMap)
        })
        mSimpleLocationService = SimpleLocationService(this)

        mAjoutButton.setOnClickListener {
            val intent = Intent(this, AddProblemeActivity::class.java)
            startActivityForResult(intent, ADD_PROBLEME_ACTIVITY_REQUEST_CODE)
        }

        /**   val mViderButton = findViewById<Button>(R.id.vider_problemes_btn)
        mViderButton.setOnClickListener {
            mProblemeViewModel.removeAllProblemes()
        } */

        /** val mMapButton = findViewById<Button>(R.id.show_map_btn)
        mMapButton.setOnClickListener {
            if (homeView.visibility == View.VISIBLE) {
                homeView.visibility = View.GONE
                mapView.visibility = View.VISIBLE
            } else {
                mapView.visibility = View.GONE
                homeView.visibility = View.VISIBLE
            }
        } */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.populate_db_btn -> {
                mProblemeViewModel.generateProblemes(Probleme.random(10, 20), mSimpleLocationService)
                true
            }
            R.id.clear_db_btn -> {
                mProblemeViewModel.removeAllProblemes()
                true
            }
            R.id.main_activity_show_map_btn -> {
                if (homeView.visibility == View.VISIBLE) {
                    homeView.visibility = View.GONE
                    mapView.visibility = View.VISIBLE
                } else {
                    mapView.visibility = View.GONE
                    homeView.visibility = View.VISIBLE
                }
                true
            }else ->
                super.onOptionsItemSelected(item)
        }
    }

    private fun updateProblemesMarker(problemes: List<Probleme>, googleMap : GoogleMap) {
        googleMap.clear()
        problemes.forEach {
            Log.d("MAPSACTIVITY", it.toString())
            googleMap.addMarker(MarkerOptions().position(LatLng(it.position_lat, it.position_long)).title(it.type))
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GEOCENTER, ZOOM_LEVEL))
    }

    /**
     * Overriding the back button from the naviguation bar
     * if the map is visible, the screen displayed will be the home view
     * otherwise it will perform the super method
     */
    override fun onBackPressed() {
        if (homeView.visibility == View.VISIBLE) {
            super.onBackPressed()
        } else {
            mapView.visibility = View.GONE
            homeView.visibility = View.VISIBLE
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PROBLEME_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // New probleme added
            //Toast.makeText(this, "Probleme ajouté", Toast.LENGTH_SHORT).show()
        } else if (requestCode == DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Received probleme to delete
            mProblemeViewModel.removeProbleme(data!!.getLongExtra(DetailProblemeActivity.EXTRA_REPLY_PROBLEME_ID, 0).toInt())
            //Toast.makeText(this, "Probleme supprimé", Toast.LENGTH_SHORT).show()
        }
    }

}
