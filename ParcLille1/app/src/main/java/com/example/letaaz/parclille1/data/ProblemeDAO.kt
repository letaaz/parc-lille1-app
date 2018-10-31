package com.example.letaaz.parclille1.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ProblemeDAO {

    @Query("select * from problemes")
    fun getAllProblemes(): LiveData<List<Probleme>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProbleme(prob : Probleme)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProbleme(prob : Probleme)

    @Delete
    fun removeProbleme(prob : Probleme)

    @Query("delete from problemes")
    fun removeAllProbleme()
}