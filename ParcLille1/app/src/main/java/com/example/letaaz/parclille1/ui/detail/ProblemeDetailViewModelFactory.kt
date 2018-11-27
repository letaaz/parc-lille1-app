package com.example.letaaz.parclille1.ui.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.letaaz.parclille1.data.ProblemeRepository

class ProblemeDetailViewModelFactory(
        private val problemeRepository : ProblemeRepository,
        private val problemeId : Int
) : ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProblemeDetailViewModel(problemeRepository, problemeId) as T
    }
}