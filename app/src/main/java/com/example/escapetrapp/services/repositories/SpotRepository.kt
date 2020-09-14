package com.example.escapetrapp.services.repositories

import android.content.ContentValues
import android.content.Context
import com.example.escapetrapp.services.constants.DataBaseConstants
import com.example.escapetrapp.services.models.Spot
import java.lang.Exception

class SpotRepository private constructor(context: Context){
    private var mSpotDataBaseHelper: SpotDataBaseHelper = SpotDataBaseHelper(context)

    companion object {
        private lateinit var repository: SpotRepository
        fun getInstance(context: Context): SpotRepository {
            if (!::repository.isInitialized) {
                repository = SpotRepository(context)
            }
            return repository
        }
    }

    fun save(spot: Spot): Boolean {
        return try {
            val db = mSpotDataBaseHelper.writableDatabase
            val contentValues = ContentValues()

            contentValues.put(DataBaseConstants.SPOT.COLUMNS.PLACE, spot.place)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.STARTDATE, spot.startDate)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.ENDDATE, spot.endDate)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.STARTTIME, spot.startTime)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.ENDDATE, spot.endTime)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.DESCRIPTION, spot.description)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.IDTRIP, spot.idTrip)

            db.insert(DataBaseConstants.SPOT.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(spot: Spot): Boolean {
        return try {
            val db = mSpotDataBaseHelper.writableDatabase
            val contentValues = ContentValues()

            contentValues.put(DataBaseConstants.SPOT.COLUMNS.PLACE, spot.place)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.STARTDATE, spot.startDate)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.ENDDATE, spot.endDate)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.STARTTIME, spot.startTime)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.ENDDATE, spot.endTime)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.DESCRIPTION, spot.description)
            contentValues.put(DataBaseConstants.SPOT.COLUMNS.IDTRIP, spot.idTrip)

            val seletion = DataBaseConstants.SPOT.COLUMNS.ID + " = ?"
            var args = arrayOf(spot.id.toString())

            db.update(DataBaseConstants.SPOT.TABLE_NAME, contentValues, seletion, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mSpotDataBaseHelper.writableDatabase

            val seletion = DataBaseConstants.SPOT.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            db.delete(DataBaseConstants.SPOT.TABLE_NAME, seletion, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun get(id: Int): Spot? {

        var spot: Spot? = null
        return try {
            val db = mSpotDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.SPOT.COLUMNS.PLACE,
                DataBaseConstants.SPOT.COLUMNS.STARTDATE,
                DataBaseConstants.SPOT.COLUMNS.ENDDATE,
                DataBaseConstants.SPOT.COLUMNS.STARTTIME,
                DataBaseConstants.SPOT.COLUMNS.ENDTIME,
                DataBaseConstants.SPOT.COLUMNS.DESCRIPTION,
                DataBaseConstants.SPOT.COLUMNS.IDTRIP
            )
            val seletion = DataBaseConstants.SPOT.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.SPOT.TABLE_NAME,
                projection, seletion, args, null, null, null
            )
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val place =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.PLACE))
                val startDate =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.STARTDATE))
                val endDate =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.ENDDATE))
                val startTime =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.STARTTIME))
                val endTime =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.ENDTIME))
                val description =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.DESCRIPTION))
                val idTrip =
                    cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.IDTRIP))

                spot = Spot(id, place, startDate, endDate, startTime, endTime, description, idTrip)
            }
            cursor?.close()
            spot
        } catch (e: Exception) {
            spot
        }
    }

    fun getAllSpots(): List<Spot> {
        val list: MutableList<Spot> = ArrayList()
        return try {
            val db = mSpotDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.SPOT.COLUMNS.ID,
                DataBaseConstants.SPOT.COLUMNS.PLACE,
                DataBaseConstants.SPOT.COLUMNS.STARTDATE,
                DataBaseConstants.SPOT.COLUMNS.ENDDATE,
                DataBaseConstants.SPOT.COLUMNS.STARTTIME,
                DataBaseConstants.SPOT.COLUMNS.ENDTIME,
                DataBaseConstants.SPOT.COLUMNS.DESCRIPTION,
                DataBaseConstants.SPOT.COLUMNS.IDTRIP
            )

            val cursor = db.query(
                DataBaseConstants.SPOT.TABLE_NAME,
                projection, null, null, null, null, null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.ID))
                    val place = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.PLACE))
                    val startDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.STARTDATE))
                    val endDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.ENDDATE))
                    val startTime = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.STARTTIME))
                    val endTime = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.ENDTIME))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.DESCRIPTION))
                    val idTrip = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPOT.COLUMNS.IDTRIP))

                    val spot = Spot(id, place, startDate, endDate, startTime, endTime,description, idTrip)
                    list.add(spot)
                }
            }
            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }
}