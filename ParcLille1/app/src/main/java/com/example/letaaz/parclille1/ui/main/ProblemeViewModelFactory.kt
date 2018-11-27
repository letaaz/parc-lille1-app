package com.example.letaaz.parclille1.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.letaaz.parclille1.data.ProblemeRepository
import com.example.letaaz.parclille1.ui.detail.ProblemeDetailViewModel
import com.example.letaaz.parclille1.ui.ui.ProblemeViewModel

class ProblemeViewModelFactory(
        private val problemeRepository: ProblemeRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProblemeViewModel(problemeRepository) as T
    }
}