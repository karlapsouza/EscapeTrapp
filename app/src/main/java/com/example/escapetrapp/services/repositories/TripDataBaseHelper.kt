package com.example.escapetrapp.services.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.escapetrapp.services.constants.DataBaseConstants

//Classe usada para acessar o banco

class TripDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_TRIP)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Trip.db"

        private const val CREATE_TABLE_TRIP = (
                "create table " + DataBaseConstants.TRIP.TABLE_NAME + "("
                    + DataBaseConstants.TRIP.COLUMNS.ID + " integer primary key autoincrement, "
                    + DataBaseConstants.TRIP.COLUMNS.NAME + " text, "
                    + DataBaseConstants.TRIP.COLUMNS.DESTINATION + " text, "
                    + DataBaseConstants.TRIP.COLUMNS.INITIAL_DATE + " text, "
                    + DataBaseConstants.TRIP.COLUMNS.END_DATE + " text); "

                )
    }

}