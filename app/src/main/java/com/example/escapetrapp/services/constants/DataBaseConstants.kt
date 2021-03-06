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
    object SPENDING{
        const val TABLE_NAME = "Spending"

        object COLUMNS{
            const val ID = "id"
            const val DESCRIPTION = "description"
            const val VALUE = "value"
            const val DATE = "date"
            const val CURRENCY = "currency"
            const val IDTRIP = "idTrip"
        }
    }
    object SPOT{
        const val TABLE_NAME = "Spot"

        object COLUMNS{
            const val ID = "id"
            const val PLACE = "place"
            const val STARTDATE = "startDate"
            const val ENDDATE = "endDate"
            const val STARTTIME = "startTime"
            const val ENDTIME = "endTime"
            const val DESCRIPTION = "description"
            const val IDTRIP = "idTrip"
        }
    }
}