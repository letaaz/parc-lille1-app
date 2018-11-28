package com.example.letaaz.parclille1.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.letaaz.parclille1.data.ProblemeRepository

/**
 * Factory class responsible for creating a ProblemeViewModel object
 */
class ProblemeViewModelFactory(
        private val problemeRepository: ProblemeRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProblemeViewModel(problemeRepository) as T
    }
}