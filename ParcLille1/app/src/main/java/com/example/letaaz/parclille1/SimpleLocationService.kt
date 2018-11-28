package com.example.letaaz.parclille1

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

class SimpleLocationService(context : Context) {

    private var mContext : Context
    private var mGeocoder : Geocoder
    private val LOCATION_PERMISSION =  arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    private val REQUEST_LOCATION_PERMISSION_CODE = 1
    private lateinit var mLocationManager : LocationManager

    init {
        mContext = context
        mGeocoder = Geocoder(context, Locale.getDefault())

    }

    /**
     * Retrive a string representing the address line of the location given in parameter
     * Using Geocoder object, the location retrieval could not work sometimes
     */
    fun retrieveAddressFromLocation(coordinate : Location) : String {
        try {
            val adresses = mGeocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 2)
            if (adresses.size > 0) {
                Log.d("LOCATION", "found multiple adresses")
                return adresses[0].getAddressLine(0)
            } else {
                Log.d("LOCATION", "no adresses found")
                return "${coordinate.latitude},${coordinate.longitude}"
            }
        } catch (e : IOException) {
            return "Erreur de localisation"
        }
    }

    /**
     * Asks the user to activate Location permission if it's not
     */
    fun requestPermissionIfNeeded(activity : Activity) {
        if (!isPermissionEnabled())
            ActivityCompat.requestPermissions(activity, LOCATION_PERMISSION, REQUEST_LOCATION_PERMISSION_CODE)
    }

    /**
     * Checks whether the location permission is granted
     */
    fun isPermissionEnabled() : Boolean{
        return ContextCompat.checkSelfPermission(mContext, LOCATION_PERMISSION[0]) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, LOCATION_PERMISSION[1]) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Checks whether the location is available on the device
     */
    fun isLocationEnabled(): Boolean {
        mLocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}