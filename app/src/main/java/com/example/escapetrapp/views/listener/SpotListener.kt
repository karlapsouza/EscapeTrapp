package com.example.escapetrapp.views.listener

interface SpotListener {
    fun onClick(id: Int){

    }

    fun onClick(idSpot: Int, idTrip: Int){

    }

    fun onDelete(id: Int){

    }
}