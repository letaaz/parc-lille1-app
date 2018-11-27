package com.example.letaaz.parclille1.data

import android.arch.persistence.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

/**
 * This class purpose it to provide a converter to the Room database
 * Converting LatLng type to String like "lat,long" where lat and long are resp the lat and long value
 */
class LatLngTypeConverter {
    @TypeConverter
    fun fromLatLng(value : LatLng) : String {
        return "" + value.latitude + "," + value.longitude
    }

    @TypeConverter
    fun toLatLng(value : String) : LatLng {
        val stringArray = value.split(",")
        return LatLng(stringArray[0].toDouble(), stringArray[1].toDouble())
    }
}