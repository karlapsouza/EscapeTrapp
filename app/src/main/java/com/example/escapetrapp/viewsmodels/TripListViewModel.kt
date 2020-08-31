package com.example.escapetrapp.viewsmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.services.repositories.TripRepository

class TripListViewModel(application: Application) : AndroidViewModel(application) {

    private val mTripRepository = TripRepository.getInstance(application.applicationContext)
    private val mTripList = MutableLiveData<List<Trip>>()
    val tripList: LiveData<List<Trip>> = mTripList

    fun load(){
        mTripList.value = mTripRepository.getAllTrips()
    }
}