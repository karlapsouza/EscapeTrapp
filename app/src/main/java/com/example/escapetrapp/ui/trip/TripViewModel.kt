package com.example.escapetrapp.ui.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.models.Trip
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class TripViewModel: ViewModel(){
    private val db = FirebaseFirestore.getInstance()
    val travelState = MutableLiveData<RequestState<FirebaseUser>>()

    fun travel(newTrip: Trip) {
        travelState.value = RequestState.Loading
        if (validateFields(newTrip)) {
            saveInFirestore(newTrip)
        } else {
            travelState.value = RequestState.Error(Throwable())
        }
    }

    private fun validateFields(newTrip: Trip): Boolean {
        return true
    }


    private fun saveInFirestore(newTrip: Trip) {
        db.collection("trip")
            .add(newTrip)
            .addOnSuccessListener { }
            .addOnFailureListener { e -> travelState.value = RequestState.Error(Throwable(e.message) )}
    }
}