package com.example.letaaz.parclille1

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build.VERSION_CODES.O
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.letaaz.parclille1.AddProblemeActivity.Companion.LOCATION_PERMISSION
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.ui.ui.ProblemeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.*

class AddProblemeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY_1: String = "com.example.letaaz.parclille1.REPLY_1"
        const val EXTRA_REPLY_2: String = "com.example.letaaz.parclille1.REPLY_2"
        const val COARSE_PERMISSION: String = "android.permission.ACCESS_COARSE_LOCATION"
        const val FINE_PERMISSION: String = "android.permission.ACCESS_FINE_LOCATION"
        const val LOCATION_PERMISSION: Int = 1
    }

    private var mSpinner : Spinner? = null
    private var mPosition : TextView? = null
    private lateinit var mLocation : Location
    private var mDesc : TextView? = null
    private lateinit var mSimpleLocationService: SimpleLocationService
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mProblemeViewModel: ProblemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        mSpinner = findViewById(R.id.form_probleme_type_spinner)
        mPosition = findViewById(R.id.form_probleme_location_text)
        mDesc = findViewById(R.id.form_probleme_description)

        mProblemeViewModel = ViewModelProviders.of(this).get(ProblemeViewModel::class.java)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mSimpleLocationService = SimpleLocationService(this)

        val locationBtn = findViewById<Button>(R.id.form_probleme_location_btn)
        locationBtn.setOnClickListener {
            mSimpleLocationService.requestPermissionIfNeeded(this)
            mFusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    mLocation = it
                    mPosition!!.text = mSimpleLocationService.retrieveAddressFromLocation(it)
                } else
                    Toast.makeText(this, "Aucun résultat trouvé", LENGTH_SHORT).show()
            }
        }

        val validation_btn = findViewById<Button>(R.id.form_probleme_validation_btn)
        validation_btn.setOnClickListener {
            val replyIntent = Intent()
            // form checking
             if (mPosition!!.text.isEmpty()) {
                 Toast.makeText(applicationContext, "La position n'a pas été renseignée", LENGTH_SHORT).show()
                 return@setOnClickListener
             }
            else { // Expecting a coordinate text such as lat,long
                 val types = resources.getStringArray(R.array.probleme_type)
                 val type = types[mSpinner!!.selectedItemPosition]
                 val prob = Probleme(type, mLocation.latitude, mLocation.longitude, mPosition!!.text.toString(), mDesc!!.text.toString())
                 mProblemeViewModel.addProbleme(prob)
                 setResult(Activity.RESULT_OK, replyIntent)
                 finish()
             }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            LOCATION_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED))
                    Toast.makeText(applicationContext, "L'application requiert votre position", LENGTH_SHORT).show()
            }
        }
    }

}