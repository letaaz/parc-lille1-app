package com.example.letaaz.parclille1.data

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * This class purpose it to provide a converter to the Room database
 * Converting Date type to Long
 */
class DateTypeConverter {
    @TypeConverter
    fun fromDate(date : Date) : Long {
        return date.time
    }

    @TypeConverter
    fun fromLong(dateLong : Long) : Date {
        return Date(dateLong)
    }
}