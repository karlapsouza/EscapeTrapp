package com.example.escapetrapp.services.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.escapetrapp.services.constants.DataBaseConstants

class SpendingDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SPENDING)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Spending.db"

        private const val CREATE_TABLE_SPENDING = (
                "create table " + DataBaseConstants.SPENDING.TABLE_NAME + "("
                        + DataBaseConstants.SPENDING.COLUMNS.ID + " integer primary key autoincrement, "
                        + DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION + " text, "
                        + DataBaseConstants.SPENDING.COLUMNS.VALUE + " text, "
                        + DataBaseConstants.SPENDING.COLUMNS.DATE + " text, "
                        + DataBaseConstants.SPENDING.COLUMNS.CURRENCY + " integer); "
                )
    }
}