package com.example.letaaz.parclille1.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask

@Database(entities = arrayOf(Probleme::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun problemeDAO() : ProblemeDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "word_database")
                                .addCallback(object : RoomDatabase.Callback() {
                                    override fun onCreate(db: SupportSQLiteDatabase) {
                                        PopulateDbAsync(instance!!).execute()
                                    }
                                })
                                .build()
                    }
                }
            }
            return instance!!
        }
    }
}

class PopulateDbAsync(db : AppDatabase) : AsyncTask<Void, Void, Void> () {
    private val mDao: ProblemeDAO = db.problemeDAO()

    override fun doInBackground(vararg p0: Void?): Void? {
        mDao.removeAllProbleme()
        return null
    }
}


