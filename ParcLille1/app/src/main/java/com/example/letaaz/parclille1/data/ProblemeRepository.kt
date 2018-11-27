package com.example.letaaz.parclille1.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

/*
 *  This class acts as an API between the viewModel and the model DAO
 */
class ProblemeRepository(problemeDAO: ProblemeDAO) {

    private val mProblemeDAO : ProblemeDAO

    init {
        mProblemeDAO = problemeDAO
    }

    fun getAllProblemes() =
            mProblemeDAO.getAllProblemes()

    fun getProbleme(problemeId: Int) =
            mProblemeDAO.getProbleme(problemeId) //Instead of using RetrieveAsyncTask(mProblemeDAO).execute(prob)

    fun addProbleme(prob : Probleme) {
        InsertAsyncTask(mProblemeDAO).execute(prob)
    }

    fun removeAllProblemes() {
        RemoveAsyncTask(mProblemeDAO).execute()
    }

    fun removeProbleme(prob: Probleme) {
        RemoveAsyncTask(mProblemeDAO).execute(prob)
    }

    fun removeProbleme(problemeId: Int) {
        RemoveWithIdAsyncTask(mProblemeDAO).execute(problemeId)
    }

    companion object {

        @Volatile private var instance: ProblemeRepository? = null

        fun getInstance(problemeDAO: ProblemeDAO) =
            instance ?: synchronized(this) {
                instance ?: ProblemeRepository(problemeDAO).also { instance = it }
            }
    }

}

/**
 * This class run the insertion operation in background allowing the UI thread to be free from
 * long running queries
 */
class InsertAsyncTask(problemeDAO: ProblemeDAO): AsyncTask<Probleme, Void, Void>() {
    private val mAsyncTaskDAO : ProblemeDAO = problemeDAO
    override fun doInBackground(vararg params: Probleme?): Void? {
        mAsyncTaskDAO.addProbleme(params[0]!!)
        return null
    }
}

/**
 * This class run the deletion operation in background allowing the UI thread to be free from
 * long running queries
 */
class RemoveAsyncTask(problemeDAO: ProblemeDAO): AsyncTask<Probleme?, Void, Void>() {
    private val mAsyncTaskDAO : ProblemeDAO = problemeDAO
    override fun doInBackground(vararg params: Probleme?): Void? {
        if (params.size == 0)
            mAsyncTaskDAO.removeAllProblemes()
        else
            mAsyncTaskDAO.removeProbleme(params[0]!!)
        return null
    }
}

class RemoveWithIdAsyncTask(problemeDAO: ProblemeDAO): AsyncTask<Int, Void, Void>() {
    private val mAsyncTaskDAO : ProblemeDAO = problemeDAO
    override fun doInBackground(vararg params: Int?): Void? {
        mAsyncTaskDAO.removeProbleme(params[0]!!)
        return null
    }
}

class RetrieveAsyncTask(problemeDAO: ProblemeDAO): AsyncTask<Int, Void, LiveData<Probleme>>() {
    private val mAsyncTaskDAO : ProblemeDAO = problemeDAO
    override fun doInBackground(vararg params: Int?): LiveData<Probleme>? {
        return mAsyncTaskDAO.getProbleme(params[0]!!)
    }

}