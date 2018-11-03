package com.example.letaaz.parclille1.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomMasterTable.TABLE_NAME
import android.arch.persistence.room.migration.Migration
import java.sql.Types.DOUBLE
import java.sql.Types.NVARCHAR

class Migration1to2 : Migration(1, 2) {

    /**
     * Defines the operation to do when migrating the database fomr version 1 to 2
     * Here we change the position type from String to ???
     */
    override fun migrate(database: SupportSQLiteDatabase) {
        val TABLE_NAME_TEMP = "probleme_temp_db"

        // 1. Create a new temp database
        database.execSQL("CREATE TABLE $TABLE_NAME_TEMP ( type TEXT, position_lat DOUBLE, position_long DOUBLE, adresse TEXT, description TEXT )")

        // 2. Remove the old one
        database.execSQL("DROP TABLE $TABLE_NAME")

        // 3. Changing the new database name to the correct one
        database.execSQL("ALTER TABLE $TABLE_NAME_TEMP RENAME TO $TABLE_NAME")

    }
}