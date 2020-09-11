package com.example.escapetrapp.services.models

data class Trip(
    val id: Int = 0,
    var name: String? = null,
    var destination: String? = null,
    var initialDate: String? = null,
    var endDate: String? = null
)