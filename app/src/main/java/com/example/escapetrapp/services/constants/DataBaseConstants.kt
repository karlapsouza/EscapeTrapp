package com.example.escapetrapp.services.constants

class DataBaseConstants {
    object TRIP{
        const val TABLE_NAME = "Trip"

        object COLUMNS{
            const val ID = "id"
            const val NAME = "name"
            const val DESTINATION = "destination"
            const val INITIAL_DATE = "initialDate"
            const val END_DATE = "endDate"
        }
    }
}