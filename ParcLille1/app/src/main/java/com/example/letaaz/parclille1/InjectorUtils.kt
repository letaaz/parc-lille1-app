package com.example.letaaz.parclille1

import android.content.Context
import com.example.letaaz.parclille1.data.AppDatabase
import com.example.letaaz.parclille1.data.ProblemeRepository
import com.example.letaaz.parclille1.ui.detail.ProblemeDetailViewModelFactory
import com.example.letaaz.parclille1.ui.main.ProblemeViewModelFactory
import com.example.letaaz.parclille1.ui.ui.ProblemeViewModel

object InjectorUtils {

    private fun getProblemeRepository(context : Context) =
            ProblemeRepository.getInstance(AppDatabase.getDatabase(context).problemeDAO())

    fun provideProblemeDetailViewModelFactory(context: Context, problemeId : Int) =
            ProblemeDetailViewModelFactory(getProblemeRepository(context), problemeId)

    fun provideProblemeViewModelFactory(context: Context) =
            ProblemeViewModelFactory(getProblemeRepository(context))
}