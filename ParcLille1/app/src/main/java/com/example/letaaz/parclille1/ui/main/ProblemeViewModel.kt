package com.example.letaaz.parclille1.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.data.ProblemeRepository

/**
 * This class represents the probleme ViewModel
 * Its much like a wrapper for the ProblemeRepository class
 */
class ProblemeViewModel(application: Application) : AndroidViewModel(application) {
    private val mProblemeRepository : ProblemeRepository = ProblemeRepository(application)
    var mProblemes: LiveData<List<Probleme>>

    init {
        mProblemes = mProblemeRepository.getAllProblemes()
    }

    fun addProbleme(prob : Probleme) {
        mProblemeRepository.addProbleme(prob)
    }

    fun removeAllProblemes() {
        mProblemeRepository.removeAllProblemes()
    }

    fun removeProbleme(prob: Probleme) {
        mProblemeRepository.removeProbleme(prob)
    }

}
