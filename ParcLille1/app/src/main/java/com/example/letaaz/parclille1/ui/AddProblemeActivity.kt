package com.example.letaaz.parclille1.ui

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.letaaz.parclille1.InjectorUtils
import com.example.letaaz.parclille1.R
import com.example.letaaz.parclille1.SimpleLocationService
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.ui.main.ProblemeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class AddProblemeActivity : AppCompatActivity() {

    companion object {
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

        val factory = InjectorUtils.provideProblemeViewModelFactory(this)
        mProblemeViewModel = ViewModelProviders.of(this, factory).get(ProblemeViewModel::class.java)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mSimpleLocationService = SimpleLocationService(this)

        val locationBtn = findViewById<Button>(R.id.form_probleme_location_btn)
        locationBtn.setOnClickListener { it ->
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
                 val timestamp = Calendar.getInstance().time
                 val prob = Probleme(type, mLocation.latitude, mLocation.longitude, mPosition!!.text.toString(), timestamp, mDesc!!.text.toString())
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
