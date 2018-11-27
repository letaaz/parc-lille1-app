package com.example.letaaz.parclille1.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
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
}

