package com.example.letaaz.parclille1.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.example.letaaz.parclille1.InjectorUtils
import com.example.letaaz.parclille1.R
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.ui.detail.ProblemeDetailViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailProblemeActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val EXTRA_REPLY_PROBLEME_ID: String = "com.example.letaaz.parclille1.PROBLEME_ID"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mapView : View
    private lateinit var homeView : View

    private var mContext: Context? = null
    private var mPosition:TextView? = null
    private var mType: TextView? = null
    private var mContainerType : LinearLayout? = null
    private var mAddresse: TextView? = null
    private var mDesc: TextView? = null
    private var mDate: TextView? = null
   // private var mButton: Button? = null
    private lateinit var mProblemeDetailViewModel: ProblemeDetailViewModel
    private var mId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_probleme_activity)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mContext = this
        mPosition = findViewById(R.id.detail_probleme_position)
        mType = findViewById(R.id.detail_probleme_type)
        mContainerType = findViewById(R.id.container_detail_probleme_type)
        mAddresse = findViewById(R.id.detail_probleme_adresse)
        mDesc = findViewById(R.id.detail_probleme_description)
        mDate = findViewById(R.id.detail_probleme_date_creation)
        mId = intent.getLongExtra("PROB_ID", 0)

        mapView = findViewById(R.id.detail_map_view)
        homeView = findViewById(R.id.detail_home_view)

        /** mButton = findViewById(R.id.detail_probleme_show_map_btn)
        mButton!!.setOnClickListener {
            if (homeView.visibility == View.VISIBLE) {
                homeView.visibility = View.GONE
                mapView.visibility = View.VISIBLE
            } else {
                mapView.visibility = View.GONE
                homeView.visibility = View.VISIBLE
            }
        } */

        val factory = InjectorUtils.provideProblemeDetailViewModelFactory(this, mId.toInt())
        mProblemeDetailViewModel = ViewModelProviders.of(this, factory).get(ProblemeDetailViewModel::class.java)
        mProblemeDetailViewModel.probleme.observe(this, Observer {
            if(it != null) {
                updateFields(it)
                updateMapMarker(it, mMap)
            }
        })


        val suppr_btn = findViewById<ImageButton>(R.id.supprimer_probleme_btn)
        suppr_btn.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Voulez-vous supprimer ce problème ?")
                    .setCancelable(false)
                    .setPositiveButton("Continuer") { _,_ ->
                        run {
                            val replyIntent = Intent()
                            replyIntent.putExtra(EXTRA_REPLY_PROBLEME_ID, mId)
                            setResult(Activity.RESULT_OK, replyIntent)
                            finish()
                        }
                    }
                    .setNegativeButton("Annuler") { dialog, _ -> dialog.cancel()
                    }

            val alert = dialogBuilder.create()
            alert.setTitle("Alerte de confirmation")
            alert.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.detail_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.detail_activity_show_map_btn -> {
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

    private fun updateMapMarker(probleme: Probleme, googleMap : GoogleMap) {
        googleMap.clear()
        var pos = LatLng(probleme.position_lat, probleme.position_long)
        googleMap.addMarker(MarkerOptions().position(pos).title(probleme.type))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 7f))
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    /**
     * Update the view fields according to probleme arg
     */
    private fun updateFields(probleme: Probleme) {
        mType!!.text = probleme.type
        mContainerType!!.setBackgroundColor(InjectorUtils.problemeTypeColor(mContext!!, probleme.type))
        mPosition!!.text = "${probleme.position_lat} (lat), ${probleme.position_long} (long)"
        mAddresse!!.text = "${probleme.adresse}"
        mDesc!!.text = probleme.description
        mDate!!.text = "Ajouté le ${MainActivity.DATE_FORMAT.format(probleme.date)}"
    }
}