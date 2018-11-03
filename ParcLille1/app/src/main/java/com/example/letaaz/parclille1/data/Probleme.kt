package com.example.letaaz.parclille1.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * Definition of a Probleme object
 */
@Entity(tableName = "problemes")
data class Probleme(

     //   @ColumnInfo(name = "id")
     //   @PrimaryKey(autoGenerate = true) var id: Long,
     //   @ColumnInfo(name = "date_creation") var date: Date,
     //   @ColumnInfo(name = "archive") var archive: Boolean,
        @PrimaryKey
        @ColumnInfo(name = "type")              var type : String,
        @ColumnInfo(name = "position_lat")      var position_lat : Double = 50.56799919912838,
        @ColumnInfo(name = "position_long")     var position_long : Double = 23.9306263737086056,
        @ColumnInfo(name = "adresse")           var adresse : String = "",
        @ColumnInfo(name = "description")       var description : String = "")

