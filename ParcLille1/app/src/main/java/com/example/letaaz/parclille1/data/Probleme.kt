package com.example.letaaz.parclille1.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * Definition of a Probleme object
 */
@Entity(tableName = "problemes")
data class Probleme(
     //   @ColumnInfo(name = "archive") var archive: Boolean,
        @ColumnInfo(name = "type")              var type : String,
        @ColumnInfo(name = "position_lat")      var position_lat : Double,
        @ColumnInfo(name = "position_long")     var position_long : Double,
        @ColumnInfo(name = "adresse")           var adresse : String = "",
        @ColumnInfo(name = "date_creation")     var date: Date,
        @ColumnInfo(name = "description")       var description : String = "") {

        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0

        companion object {
                /**
                 * Returning a random integer between start and endInclusive
                 */
                fun random(start : Int, endInclusive : Int) =
                        Random().nextInt((endInclusive + 1) - start) +  start

                /**
                 * Returnin a random coordinates in the circle defined by a center and a radius
                 */
                fun randomCoordinates(center: LatLng, radius: Int) : LatLng {
                        val x0 = center.latitude
                        val y0 =center.longitude

                        val radiusInDegrees = (radius / 111000f)

                        val u = Random().nextDouble()
                        val v = Random().nextDouble()
                        val w = radiusInDegrees * Math.sqrt(u)
                        val t = 2.0 * Math.PI * v
                        val x = w * Math.cos(t)
                        val y = w * Math.sin(t)

                        val new_x = x / Math.cos(y0)

                        val foundLatitude = new_x + x0
                        val foundLongitude = y + y0
                        return LatLng(foundLatitude, foundLongitude)
                }

                /**
                 * Returning a random probleme type string
                 */
                fun randomType() : String{
                        val i = random(0, 5)
                        return arrayOf("Arbre à tailler", "Arbre à abattre", "Détritus", "Haie à tailler", "Mauvaise herbe", "Autre")[i]
                }
        }
}

