package com.example.escapetrapp.viewsmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.escapetrapp.services.models.Spot
import com.example.escapetrapp.services.repositories.SpotRepository

class SpotListViewModel (application: Application) : AndroidViewModel(application) {
    private val mSpotRepository = SpotRepository.getInstance(application.applicationContext)
    private val mSpotList = MutableLiveData<List<Spot>>()
    val spotList: LiveData<List<Spot>> = mSpotList

    fun load(){
        val list = mSpotRepository.getAllSpots()
        mSpotList.value = list
    }

    fun delete(id: Int){
        mSpotRepository.delete(id)
    }
}