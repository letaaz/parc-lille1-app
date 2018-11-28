package com.example.letaaz.parclille1.ui.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import com.example.letaaz.parclille1.ui.MainActivity
import com.example.letaaz.parclille1.SimpleLocationService
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.data.ProblemeRepository
import java.util.*

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

    fun generateProblemes(number : Int, ssl : SimpleLocationService) {
        for (i in 1..number) {
            var fakeType = Probleme.randomType()
            var fakePosition = Probleme.randomCoordinates(MainActivity.GEOCENTER, 10000)
            var fakeAddress = Location("")
            fakeAddress.latitude = fakePosition.latitude
            fakeAddress.longitude = fakePosition.longitude
            var prob = Probleme(fakeType, fakePosition.latitude, fakePosition.longitude,
                            ssl.retrieveAddressFromLocation(fakeAddress), Calendar.getInstance().time,
                    "Description auto-générée concernant se problème ...")
            addProbleme(prob)
        }
    }

}
