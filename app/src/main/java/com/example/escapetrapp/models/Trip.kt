package com.example.escapetrapp.models

import com.google.firebase.Timestamp

data class Trip(
    val name: String? = null,
    val destination: String? = null,
    val initialDate: String? = null,
    val endDate: String? = null
)