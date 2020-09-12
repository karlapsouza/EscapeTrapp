package com.example.escapetrapp.services.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.escapetrapp.services.constants.DataBaseConstants

class SpotDataBaseHelper(context: Context) : SQLiteOpenHelper(context, SpotDataBaseHelper.DATABASE_NAME, null, SpotDataBaseHelper.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SPOT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Spot.db"

        private const val CREATE_TABLE_SPOT = (
                "create table " + DataBaseConstants.TRIP.TABLE_NAME + "("
                        + DataBaseConstants.SPOT.COLUMNS.ID + " integer primary key autoincrement, "
                        + DataBaseConstants.SPOT.COLUMNS.PLACE + " text, "
                        + DataBaseConstants.SPOT.COLUMNS.STARTDATE + " text, "
                        + DataBaseConstants.SPOT.COLUMNS.ENDDATE + " text, "
                        + DataBaseConstants.SPOT.COLUMNS.STARTTIME + " text, "
                        + DataBaseConstants.SPOT.COLUMNS.ENDTIME + " text, "
                        + DataBaseConstants.SPOT.COLUMNS.IDTRIP + " integer); "

                )
    }
}