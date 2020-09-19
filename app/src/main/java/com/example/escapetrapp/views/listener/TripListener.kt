package com.example.escapetrapp.views.listener

import com.example.escapetrapp.services.models.Trip

interface TripListener {
    fun onClick(id: Int){

    }

    fun onDelete(id: Int){

    }

    fun onUpdate(id: Int){

    }

    fun onView(id: Int){

    }
}