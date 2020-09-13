package com.example.escapetrapp.viewsmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Spot
import com.example.escapetrapp.services.repositories.SpotRepository

class SpotViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application.applicationContext
    private val mSpotRepository : SpotRepository = SpotRepository.getInstance(mContext)
    val spotState = MutableLiveData<RequestState<String>>()

    fun addSpot(newSpot: Spot) {
        spotState.value = RequestState.Loading
        if(validateFields(newSpot)) {
            save(newSpot)
        }else {
            spotState.value = RequestState.Error(Throwable())
        }
    }

    fun updateSpot(spot: Spot) {
        spotState.value = RequestState.Loading
        if(validateFields(spot)) {
            update(spot)
        }else {
            spotState.value = RequestState.Error(Throwable())
        }
    }

    private fun validateFields(spot: Spot): Boolean {
        if(spot.place?.isEmpty() == true){
            spotState.value = RequestState.Error(Throwable("Informe o nome do lugar"))
            return false
        }
        if(spot.startDate?.isEmpty() == true){
            spotState.value = RequestState.Error(Throwable("Informe a data inicial"))
            return false
        }
        if(spot.endDate?.isEmpty() == true){
            spotState.value = RequestState.Error(Throwable("Informe a data final"))
            return false
        }
        if(spot.startTime?.isEmpty() == true){
            spotState.value = RequestState.Error(Throwable("Informe a hora inicial"))
            return false
        }
        if(spot.endTime?.isEmpty() == true){
            spotState.value = RequestState.Error(Throwable("Informe a hora final"))
            return false
        }
        return true
    }

    fun save(newSpot: Spot){
        if(mSpotRepository.save(newSpot) == true) {
            spotState.value = RequestState.Success("Atividade cadastrada")
        }else{
            spotState.value = RequestState.Error(Throwable("Não foi possível cadastrar atividade"))
        }
    }

    fun update(spot: Spot){
        if(mSpotRepository.update(spot) == true) {
            spotState.value = RequestState.Success("Atividade atualizada")
        }else{
            spotState.value = RequestState.Error(Throwable("Não foi possível atualizar a atividade"))
        }
    }

}