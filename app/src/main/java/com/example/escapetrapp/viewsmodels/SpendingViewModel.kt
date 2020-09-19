package com.example.escapetrapp.viewsmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.escapetrapp.services.constants.TripConstants
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.services.repositories.SpendingRepository
import com.example.escapetrapp.services.repositories.TripRepository


class SpendingViewModel(application: Application) : AndroidViewModel(application){

    val spendingState = MutableLiveData<RequestState<String>>()
    private val mSpendingRepository = SpendingRepository.getInstance(application.applicationContext)
    private val mTripRepository = TripRepository.getInstance(application.applicationContext)
    private val mSpendingList = MutableLiveData<List<Spending>>()
    val spendingList: LiveData<List<Spending>> = mSpendingList
    private val mSpending = MutableLiveData<Spending>()
    val oneSpending: LiveData<Spending> = mSpending

    fun load(id: Int){
        mSpending.value = mSpendingRepository.get(id)
    }

    fun loadAll(){
        val list = mSpendingRepository.getAllSpendings()
        mSpendingList.value = list
    }

    fun loadAllTrip(tripId: Int){
        val list = mSpendingRepository.getAllSpendingsTrip(tripId)
        mSpendingList.value = list
    }

    fun delete(id: Int){
        mSpendingRepository.delete(id)
    }

    fun addSpending(newSpending: Spending) {
        spendingState.value = RequestState.Loading
        if(validateFields(newSpending)) {
            saveSpending(newSpending)
        }else {
            spendingState.value = RequestState.Error(Throwable())
        }
    }

    fun updateSpending(spending: Spending) {
        spendingState.value = RequestState.Loading
        if(validateFields(spending)) {
            updateSpend(spending)
        }else {
            spendingState.value = RequestState.Error(Throwable())
        }
    }

    private fun validateFields(newSpending: Spending): Boolean {
        if(newSpending.description?.isEmpty() == true){
            spendingState.value = RequestState.Error(Throwable("Informe uma descrição"))
            return false
        }
        if(newSpending.value == null){
            spendingState.value = RequestState.Error(Throwable("Informe o valor"))
            return false
        }
        if(newSpending.date?.isEmpty() == true){
            spendingState.value = RequestState.Error(Throwable("Informe a data"))
            return false
        }
        if(newSpending.currency?.equals("") == true){
            spendingState.value = RequestState.Error(Throwable("Informe a moeda"))
            return false
        }
        return true
    }

    fun saveSpending(newSpending: Spending){
        if(mSpendingRepository.saveSpending(newSpending)) {
            spendingState.value = RequestState.Success("Despesa cadastrada")
        }else{
            spendingState.value = RequestState.Error(Throwable("Não foi possível cadastrar a despesa"))
        }
    }

    fun updateSpend(spending: Spending){
        if(mSpendingRepository.update(spending)) {
            spendingState.value = RequestState.Success("Despesa atualizada")
        }else{
            spendingState.value = RequestState.Error(Throwable("Não foi possível cadastrar a despesa"))
        }
    }

    fun getTotalSpending() : Double {
        return mSpendingRepository.getSumSpendings()
    }

    fun getTotalSpendingTrip(tripId: Int) : Double {
        return mSpendingRepository.getSumSpendingTrip(tripId)
    }

    fun getTripList() : List<Trip> {
        return mTripRepository.getAllTrips()
    }

}