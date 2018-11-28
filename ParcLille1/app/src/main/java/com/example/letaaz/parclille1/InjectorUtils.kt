package com.example.letaaz.parclille1

import android.content.Context
import com.example.letaaz.parclille1.data.AppDatabase
import com.example.letaaz.parclille1.data.ProblemeRepository
import com.example.letaaz.parclille1.ui.detail.ProblemeDetailViewModelFactory
import com.example.letaaz.parclille1.ui.main.ProblemeViewModelFactory

object InjectorUtils {

    private fun getProblemeRepository(context : Context) =
            ProblemeRepository.getInstance(AppDatabase.getDatabase(context).problemeDAO())

    fun provideProblemeDetailViewModelFactory(context: Context, problemeId : Int) =
            ProblemeDetailViewModelFactory(getProblemeRepository(context), problemeId)

    fun provideProblemeViewModelFactory(context: Context) =
            ProblemeViewModelFactory(getProblemeRepository(context))

    fun problemeTypeColor(context: Context, problemeType: String) : Int {
        context.resources.getStringArray(R.array.probleme_type).forEach {
            if (it.equals(problemeType))
                return getProblemeTypeColorMapping(context, it)
        }
        return getProblemeTypeColorMapping(context, "Autre")
    }

    private fun getProblemeTypeColorMapping(context: Context, problemeType: String) : Int {
        when(problemeType) {
            "Arbre à tailler" ->
                return context.resources.getColor(R.color.probleme_type_arbre_a_tailler)
            "Arbre à abbatre" ->
                return context.resources.getColor(R.color.probleme_type_arbre_a_abattre)
            "Détritus" ->
                return context.resources.getColor(R.color.probleme_type_detritus)
            "Haie à tailler" ->
                return context.resources.getColor(R.color.probleme_type_haie_a_tailler)
            "Mauvaise herbe" ->
                return context.resources.getColor(R.color.probleme_type_mauvaise_herbe)
            else -> // Autre
                return context.resources.getColor(R.color.probleme_type_autre)
        }
    }
}