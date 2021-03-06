package com.example.letaaz.parclille1.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/*
 *   Definition of the Probleme's Data Access Object
 *   Listing all possible operations with the database
 */
@Dao
interface ProblemeDAO {

    @Query("select * from problemes")
    fun getAllProblemes(): LiveData<List<Probleme>>

    @Query("select * from problemes where id = :prob")
    fun getProbleme(prob: Int): LiveData<Probleme>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProbleme(prob : Probleme)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProbleme(prob : Probleme)

    @Delete
    fun removeProbleme(prob : Probleme)

    @Query("delete from problemes where id = :prob")
    fun removeProbleme(prob : Int)

    @Query("delete from problemes")
    fun removeAllProblemes()
}