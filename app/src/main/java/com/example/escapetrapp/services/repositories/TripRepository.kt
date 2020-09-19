package com.example.escapetrapp.services.repositories

import android.content.ContentValues
import android.content.Context
import com.example.escapetrapp.services.constants.DataBaseConstants
import com.example.escapetrapp.services.models.Trip
import java.lang.Exception

class TripRepository private constructor(context: Context) {

    private var mTripDataBaseHelper: TripDataBaseHelper = TripDataBaseHelper(context)

    companion object {
        private lateinit var repository: TripRepository
        fun getInstance(context: Context): TripRepository {
            if (!::repository.isInitialized) {
                repository = TripRepository(context)
            }
            return repository
        }
    }

    fun save(trip: Trip): Boolean {
        return try {
            val db = mTripDataBaseHelper.writableDatabase
            val contentValues = ContentValues()

            contentValues.put(DataBaseConstants.TRIP.COLUMNS.NAME, trip.name)
            contentValues.put(DataBaseConstants.TRIP.COLUMNS.DESTINATION, trip.destination)
            contentValues.put(DataBaseConstants.TRIP.COLUMNS.INITIAL_DATE, trip.initialDate)
            contentValues.put(DataBaseConstants.TRIP.COLUMNS.END_DATE, trip.endDate)

            db.insert(DataBaseConstants.TRIP.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(trip: Trip): Boolean {
        return try {
            val db = mTripDataBaseHelper.writableDatabase
            val contentValues = ContentValues()

            contentValues.put(DataBaseConstants.TRIP.COLUMNS.NAME, trip.name)
            contentValues.put(DataBaseConstants.TRIP.COLUMNS.DESTINATION, trip.destination)
            contentValues.put(DataBaseConstants.TRIP.COLUMNS.INITIAL_DATE, trip.initialDate)
            contentValues.put(DataBaseConstants.TRIP.COLUMNS.END_DATE, trip.endDate)

            val seletion = DataBaseConstants.TRIP.COLUMNS.ID + " = ?"
            var args = arrayOf(trip.id.toString())

            db.update(DataBaseConstants.TRIP.TABLE_NAME, contentValues, seletion, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mTripDataBaseHelper.writableDatabase

            val seletion = DataBaseConstants.TRIP.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            db.delete(DataBaseConstants.TRIP.TABLE_NAME, seletion, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun get(id: Int): Trip? {

        var trip: Trip? = null
        return try {
            val db = mTripDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.TRIP.COLUMNS.NAME,
                DataBaseConstants.TRIP.COLUMNS.DESTINATION,
                DataBaseConstants.TRIP.COLUMNS.INITIAL_DATE,
                DataBaseConstants.TRIP.COLUMNS.END_DATE
            )
            val seletion = DataBaseConstants.TRIP.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.TRIP.TABLE_NAME,
                projection, seletion, args, null, null, null
            )
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val name =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.NAME))
                val destination =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.DESTINATION))
                val initialDate =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.INITIAL_DATE))
                val endDate =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.END_DATE))

                trip = Trip(id, name, destination, initialDate, endDate)
            }
            cursor?.close()
            trip
        } catch (e: Exception) {
            trip
        }
    }

    fun getAllTrips(): List<Trip> {
        val list: ArrayList<Trip> = ArrayList()
        return try {
            val db = mTripDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.TRIP.COLUMNS.ID,
                DataBaseConstants.TRIP.COLUMNS.NAME,
                DataBaseConstants.TRIP.COLUMNS.DESTINATION,
                DataBaseConstants.TRIP.COLUMNS.INITIAL_DATE,
                DataBaseConstants.TRIP.COLUMNS.END_DATE
            )

            val cursor = db.query(
                DataBaseConstants.TRIP.TABLE_NAME,
                projection, null, null, null, null, null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.NAME))
                    val destination = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.DESTINATION))
                    val initialDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.INITIAL_DATE))
                    val endDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TRIP.COLUMNS.END_DATE))

                    val trip = Trip(id, name, destination, initialDate, endDate)
                    list.add(trip)
                }
            }
            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }
}