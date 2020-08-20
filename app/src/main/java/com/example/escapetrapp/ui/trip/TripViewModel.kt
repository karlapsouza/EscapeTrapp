package com.example.escapetrapp.ui.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.models.Trip
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class TripViewModel: ViewModel(){
    private val db = FirebaseFirestore.getInstance()
    val tripState = MutableLiveData<RequestState<String>>()

    fun addTrip(newTrip: Trip) {
        tripState.value = RequestState.Loading
        if(validateFields(newTrip)) {
            saveInFirestore(newTrip)
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


    private fun saveInFirestore(newTrip: Trip) {
        db.collection("trip")
            .add(newTrip)
            .addOnSuccessListener { tripState.value = RequestState.Success("")}
            .addOnFailureListener { e -> tripState.value = RequestState.Error(Throwable(e.message) )}
    }
}