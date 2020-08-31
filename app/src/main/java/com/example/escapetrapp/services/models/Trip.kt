package com.example.escapetrapp.services.models

data class Trip(
    val id:Int = 0,
    val name: String? = null,
    val destination: String? = null,
    val initialDate: String? = null,
    val endDate: String? = null
)