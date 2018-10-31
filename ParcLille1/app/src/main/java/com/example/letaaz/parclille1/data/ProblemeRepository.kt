package com.example.letaaz.parclille1.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class ProblemeRepository(application: Application) {

    private val mProblemeDAO : ProblemeDAO
    private val mAllProblemes : LiveData<List<Probleme>>

    init {
        val db = AppDatabase.getDatabase(application)
        mProblemeDAO = db.problemeDAO()
        mAllProblemes = mProblemeDAO.getAllProblemes()
    }

    fun getAllProblemes() : LiveData<List<Probleme>> {
        return mAllProblemes
    }

    fun addProbleme(prob : Probleme) {
        InsertAsyncTask(mProblemeDAO).execute(prob)
    }

    fun removeAllProblemes() {
        RemoveAsyncTask(mProblemeDAO).execute()
    }

    fun removeProbleme(prob: Probleme) {
        RemoveAsyncTask(mProblemeDAO).execute(prob)
    }
}

class InsertAsyncTask(problemeDAO: ProblemeDAO): AsyncTask<Probleme, Void, Void>() {
    private val mAsyncTaskDAO : ProblemeDAO = problemeDAO
    override fun doInBackground(vararg params: Probleme?): Void? {
        mAsyncTaskDAO.addProbleme(params[0]!!)
        return null
    }
}

class RemoveAsyncTask(problemeDAO: ProblemeDAO): AsyncTask<Probleme?, Void, Void>() {
    private val mAsyncTaskDAO : ProblemeDAO = problemeDAO
    override fun doInBackground(vararg params: Probleme?): Void? {
        if (params.size == 0)
            mAsyncTaskDAO.removeAllProbleme()
        else
            mAsyncTaskDAO.removeProbleme(params[0]!!)
        return null
    }
}