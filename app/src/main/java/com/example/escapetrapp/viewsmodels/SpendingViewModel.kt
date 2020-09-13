package com.example.escapetrapp.viewsmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.services.repositories.SpendingRepository


class SpendingViewModel(application: Application) : AndroidViewModel(application){

    val spendingState = MutableLiveData<RequestState<String>>()
    private val mSpendingRepository = SpendingRepository.getInstance(application.applicationContext)
    private val mSpendingList = MutableLiveData<List<Spending>>()
    val spendingList: LiveData<List<Spending>> = mSpendingList

    fun load(){
        val list = mSpendingRepository.getAllSpendings()
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

    private fun validateFields(newSpending: Spending): Boolean {
        if(newSpending.description?.isEmpty() == true){
            spendingState.value = RequestState.Error(Throwable("Informe uma descrição"))
            return false
        }
        if(newSpending.value?.isNaN() == true){
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

}