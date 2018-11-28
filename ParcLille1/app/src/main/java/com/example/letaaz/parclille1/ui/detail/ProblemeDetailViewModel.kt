package com.example.letaaz.parclille1.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.data.ProblemeRepository

/**
 * This class represents the probleme detail ViewModel
 */
class ProblemeDetailViewModel(problemeRepository: ProblemeRepository,
                              problemeId : Int) : ViewModel() {

    var probleme : LiveData<Probleme>

    init {
        probleme = problemeRepository.getProbleme(problemeId)
    }
}