package com.example.escapetrapp.viewsmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.services.repositories.TripRepository

class TripViewModel(application: Application): AndroidViewModel(application){
    //private val db = FirebaseFirestore.getInstance()
    private val mContext = application.applicationContext
    private val mTripRepository : TripRepository = TripRepository.getInstance(mContext)
    val tripState = MutableLiveData<RequestState<String>>()

    fun addTrip(newTrip: Trip) {
        tripState.value = RequestState.Loading
        if(validateFields(newTrip)) {
            save(newTrip)
            //saveInFirestore(newTrip)
        }else {
            tripState.value = RequestState.Error(Throwable())
        }
    }

    private fun validateFields(newTrip: Trip): Boolean {
        if(newTrip.name?.isEmpty() == true){
            tripState.value = RequestState.Error(Throwable("Informe o nome da viagem"))
            return false
        }
        if(newTrip.destination?.isEmpty() == true){
            tripState.value = RequestState.Error(Throwable("Informe o destino"))
            return false
        }
        if(newTrip.initialDate?.isEmpty()== true){
            tripState.value = RequestState.Error(Throwable("Informe a data inicial"))
            return false
        }
        if(newTrip.endDate?.isEmpty()== true){
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

    //#Usando Firestore para salvar usuário
//    private fun save(newTrip: Trip) {
//        db.collection("trip")
//            .add(newTrip)
//            .addOnSuccessListener { tripState.value = RequestState.Success("")}
//            .addOnFailureListener { e -> tripState.value = RequestState.Error(Throwable(e.message) )}
//    }
}