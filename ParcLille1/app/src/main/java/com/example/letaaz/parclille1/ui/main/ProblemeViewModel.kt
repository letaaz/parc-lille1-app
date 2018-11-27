package com.example.letaaz.parclille1.ui.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.data.ProblemeDAO
import com.example.letaaz.parclille1.data.ProblemeRepository

/**
 * This class represents the probleme ViewModel
 * Its much like a wrapper for the ProblemeRepository class
 */
class ProblemeViewModel(probemeRepository: ProblemeRepository) : ViewModel() {
    private val mProblemeRepository : ProblemeRepository
    var mProblemes: LiveData<List<Probleme>>

    init {
        mProblemeRepository = probemeRepository
        mProblemes = mProblemeRepository.getAllProblemes()
    }

    fun addProbleme(prob : Probleme) {
        mProblemeRepository.addProbleme(prob)
    }

    fun getProbleme(problemeId: Int) =
        mProblemeRepository.getProbleme(problemeId)

    fun removeAllProblemes() {
        mProblemeRepository.removeAllProblemes()
    }

    fun removeProbleme(prob: Probleme) {
        mProblemeRepository.removeProbleme(prob)
    }

    fun removeProbleme(problemeId: Int) {
        mProblemeRepository.removeProbleme(problemeId)
    }

}
