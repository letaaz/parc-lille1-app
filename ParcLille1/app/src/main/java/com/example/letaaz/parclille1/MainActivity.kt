package com.example.letaaz.parclille1

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.ui.ui.ProblemeViewModel
import com.example.letaaz.parclille1.ui.ui.ProblemeListAdapter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val ADD_PROBLEME_ACTIVITY_REQUEST_CODE = 1
        private const val DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE = 2
        const val LOCATION_PERMISSION_REQUEST_CODE = 3
        val DATE_FORMAT = SimpleDateFormat("dd/MM/yyy 'à' HH:mm", Locale.getDefault())
        private val GEOCENTER : LatLng = LatLng(50.6233961, 3.0522625999999997)
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mapView : View
    private lateinit var homeView : View
    private lateinit var mProblemeViewModel: ProblemeViewModel

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
            intent.putExtra("PROB_TYPE", it.type)
            intent.putExtra("PROB_POSITION", "" + it.position_lat + "," + it.position_long)
            intent.putExtra("PROB_DESC", it.description)
            intent.putExtra("PROB_ADDRESSE", it.adresse)
            intent.putExtra("PROB_DATE", DATE_FORMAT.format(it.date))
            intent.putExtra("PROB_ID", it.id)
            startActivityForResult(intent, DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val mAjoutButton = findViewById<Button>(R.id.ajout_probleme_btn)
        val mViderButton = findViewById<Button>(R.id.vider_problemes_btn)
        val mMapButton = findViewById<Button>(R.id.show_map_btn)

        val factory = InjectorUtils.provideProblemeViewModelFactory(this)
        mProblemeViewModel = ViewModelProviders.of(this, factory).get(ProblemeViewModel::class.java)
        mProblemeViewModel.mProblemes.observe(this, Observer {
            adapter.setProblemes(it!!)
            updateProblemesMarker(it, mMap)
        })

        mAjoutButton.setOnClickListener {
            val intent = Intent(this, AddProblemeActivity::class.java)
            startActivityForResult(intent, ADD_PROBLEME_ACTIVITY_REQUEST_CODE)
        }

        mViderButton.setOnClickListener {
            mProblemeViewModel.removeAllProblemes()
        }

        mMapButton.setOnClickListener {
            /* val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent) */
            if (homeView.visibility == View.VISIBLE) {
                homeView.visibility = View.GONE
                mapView.visibility = View.VISIBLE
            } else {
                mapView.visibility = View.GONE
                homeView.visibility = View.VISIBLE
            }
        }
    }

    private fun updateProblemesMarker(problemes: List<Probleme>, googleMap : GoogleMap) {
        googleMap.clear()
        problemes.forEach {
            Log.d("MAPSACTIVITY", it.toString())
            googleMap.addMarker(MarkerOptions().position(LatLng(it.position_lat, it.position_long)).title(it.type))
        }
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
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(GEOCENTER))
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
            Toast.makeText(this, "Probleme ajouté", Toast.LENGTH_SHORT).show()
        } else if (requestCode == DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Probleme to delete
            mProblemeViewModel.removeProbleme(data!!.getLongExtra(DetailProblemeActivity.EXTRA_REPLY_PROBLEME_ID, 0).toInt())
            Toast.makeText(this, "Probleme supprimé", Toast.LENGTH_SHORT).show()
        }
    }

}
