package com.example.letaaz.parclille1

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.example.letaaz.parclille1.data.LatLngTypeConverter
import com.example.letaaz.parclille1.data.Probleme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY_1: String = "com.example.letaaz.parclille1.REPLY_1"
        const val EXTRA_REPLY_2: String = "com.example.letaaz.parclille1.REPLY_2"
    }

    private var mSpinner : Spinner? = null
    private var mPosition : TextView? = null
    //private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        mSpinner = findViewById(R.id.form_probleme_type_spinner)
        mPosition = findViewById(R.id.form_probleme_location_text)

        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        /* val locationBtn = findViewById<Button>(R.id.form_probleme_location_btn)
        locationBtn.setOnClickListener {
                fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                    if (location != null) {
                        mPosition!!.text = "" + location.latitude + "," + location.longitude
                    }
                }
        } */

        val validation_btn = findViewById<Button>(R.id.form_probleme_validation_btn)
        validation_btn.setOnClickListener {
            val replyIntent = Intent()
            // form checking
             if (mPosition!!.text.isEmpty())
                 replyIntent.putExtra(EXTRA_REPLY_2, "50.6233961,3.0522625999999997")
            else { // Expecting a coordinate text such as lat,long
                 //val positions = mPosition!!.text.split(",")
                 replyIntent.putExtra(EXTRA_REPLY_2, "" + mPosition!!.text) //positions[0].toDouble()+ "," + positions[1].toDouble())
             }
            val types = resources.getStringArray(R.array.probleme_type)
            val type = types[mSpinner!!.selectedItemPosition]
            replyIntent.putExtra(EXTRA_REPLY_1, type)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

}
