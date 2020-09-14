package com.example.escapetrapp.viewsmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.services.repositories.TripRepository

class TripViewModel(application: Application): AndroidViewModel(application){
    private val mContext = application.applicationContext
    private val mTripRepository : TripRepository = TripRepository.getInstance(mContext)
    val tripState = MutableLiveData<RequestState<String>>()
    val tripStateUpdate = MutableLiveData<RequestState<String>>()
    private val mTrip = MutableLiveData<Trip>()
    val oneTrip: LiveData<Trip> = mTrip
    private val mTripList = MutableLiveData<List<Trip>>()
    val tripList: LiveData<List<Trip>> = mTripList

    fun addTrip(newTrip: Trip) {
        tripState.value = RequestState.Loading
        if(validateFields(newTrip)) {
            save(newTrip)
        }else {
            tripState.value = RequestState.Error(Throwable())
        }
    }

    fun updateTrip(trip: Trip) {
        tripState.value = RequestState.Loading
        if(validateFields(trip)) {
            update(trip)
        }else {
            tripState.value = RequestState.Error(Throwable())
        }
    }

    fun delete(id: Int){
        mTripRepository.delete(id)
    }

    fun load(id: Int){
        mTrip.value = mTripRepository.get(id)
    }

    fun loadAll(){
        val list = mTripRepository.getAllTrips()
        mTripList.value = list
    }

    private fun validateFields(trip: Trip): Boolean {
        if(trip.name?.isEmpty() == true){
            tripState.value = RequestState.Error(Throwable("Informe o nome da viagem"))
            return false
        }
        if(trip.destination?.isEmpty() == true){
            tripState.value = RequestState.Error(Throwable("Informe o destino"))
            return false
        }
        if(trip.initialDate?.isEmpty()== true){
            tripState.value = RequestState.Error(Throwable("Informe a data inicial"))
            return false
        }
        if(trip.endDate?.isEmpty()== true){
            tripState.value = RequestState.Error(Throwable("Informe a data final"))
            return false
        }
        return true
    }

    fun save(newTrip: Trip){
        if(mTripRepository.save(newTrip) == true) {
            tripState.value = RequestState.Success("Viagem cadastrada")
        }else{
            tripState.value = RequestState.Error(Throwable("Não foi possível cadastrar viagem"))
        }
    }

    fun update(trip: Trip){
        if(mTripRepository.update(trip) == true) {
            tripState.value = RequestState.Success("Viagem atualizada")
        }else{
            tripState.value = RequestState.Error(Throwable("Não foi possível atualizar a viagem"))
        }
    }

}