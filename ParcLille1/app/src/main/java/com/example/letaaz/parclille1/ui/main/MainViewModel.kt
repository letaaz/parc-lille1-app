package com.example.letaaz.parclille1.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.data.ProblemeRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val mProblemeRepository : ProblemeRepository = ProblemeRepository(application)
    var mAllProblemes: LiveData<List<Probleme>>

    init {
        mAllProblemes = mProblemeRepository.getAllProblemes()
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
