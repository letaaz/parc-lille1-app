package com.example.letaaz.parclille1.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Definition of a Probleme
 */
@Entity(tableName = "problemes")
data class Probleme(

     //   @ColumnInfo(name = "id")
     //   @PrimaryKey(autoGenerate = true) var id: Long,
     //   @ColumnInfo(name = "date_creation") var date: Date,
     //   @ColumnInfo(name = "archive") var archive: Boolean,
        @PrimaryKey
        @ColumnInfo(name = "type")          var type : String,
        @ColumnInfo(name = "position")      var position : String = "",
        @ColumnInfo(name = "adresse")       var adresse : String = "",
        @ColumnInfo(name = "description")   var description : String = "")