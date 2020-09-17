package com.example.escapetrapp.services.repositories

import android.content.ContentValues
import android.content.Context
import com.example.escapetrapp.services.constants.DataBaseConstants
import com.example.escapetrapp.services.models.Spending
import java.lang.Exception

class SpendingRepository private constructor(context: Context){
    private var mSpendingDataBaseHelper: SpendingDataBaseHelper = SpendingDataBaseHelper(context)

    companion object {
        private lateinit var repository: SpendingRepository
        fun getInstance(context: Context): SpendingRepository {
            if (!::repository.isInitialized) {
                repository = SpendingRepository(context)
            }
            return repository
        }
    }

    fun saveSpending(spending: Spending): Boolean {
        return try {
            val db = mSpendingDataBaseHelper.writableDatabase
            val contentValues = ContentValues()

            contentValues.put(DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION, spending.description)
            contentValues.put(DataBaseConstants.SPENDING.COLUMNS.VALUE, spending.value)
            contentValues.put(DataBaseConstants.SPENDING.COLUMNS.DATE, spending.date)
            contentValues.put(DataBaseConstants.SPENDING.COLUMNS.CURRENCY, spending.currency)

            db.insert(DataBaseConstants.SPENDING.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(spending: Spending): Boolean {
        return try {
            val db = mSpendingDataBaseHelper.writableDatabase
            val contentValues = ContentValues()

            contentValues.put(DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION, spending.description)
            contentValues.put(DataBaseConstants.SPENDING.COLUMNS.VALUE, spending.value)
            contentValues.put(DataBaseConstants.SPENDING.COLUMNS.DATE, spending.date)
            contentValues.put(DataBaseConstants.SPENDING.COLUMNS.CURRENCY, spending.currency)

            val seletion = DataBaseConstants.SPENDING.COLUMNS.ID + " = ?"
            var args = arrayOf(spending.id.toString())

            db.update(DataBaseConstants.SPENDING.TABLE_NAME, contentValues, seletion, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mSpendingDataBaseHelper.writableDatabase

            val seletion = DataBaseConstants.SPENDING.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            db.delete(DataBaseConstants.SPENDING.TABLE_NAME, seletion, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun get(id: Int): Spending? {

        var spending: Spending? = null
        return try {
            val db = mSpendingDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION,
                DataBaseConstants.SPENDING.COLUMNS.VALUE,
                DataBaseConstants.SPENDING.COLUMNS.DATE,
                DataBaseConstants.SPENDING.COLUMNS.CURRENCY
            )
            val seletion = DataBaseConstants.SPENDING.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.SPENDING.TABLE_NAME,
                projection, seletion, args, null, null, null
            )
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val description =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION))
                val value =
                    cursor.getDouble(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.VALUE))
                val date =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.DATE))
                val currency =
                    cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.CURRENCY))

                spending = Spending(id, description, value, date, currency)
            }
            cursor?.close()
            spending
        } catch (e: Exception) {
            spending
        }
    }

    fun getAllSpendings(): List<Spending> {
        val list: MutableList<Spending> = ArrayList()
        return try {
            val db = mSpendingDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.SPENDING.COLUMNS.ID,
                DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION,
                DataBaseConstants.SPENDING.COLUMNS.VALUE,
                DataBaseConstants.SPENDING.COLUMNS.DATE,
                DataBaseConstants.SPENDING.COLUMNS.CURRENCY
            )

            val cursor = db.query(
                DataBaseConstants.SPENDING.TABLE_NAME,
                projection, null, null, null, null, null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.ID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION))
                    val value = cursor.getDouble(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.VALUE))
                    val date = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.DATE))
                    val currency = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.CURRENCY))

                    val spending = Spending(id, description, value, date, currency)
                    list.add(spending)
                }
            }
            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun getSumSpendings(): Double {
        var teste : Double = 0.0
        return try {
            val db = mSpendingDataBaseHelper.readableDatabase
            val projection = arrayOf(
                    DataBaseConstants.SPENDING.COLUMNS.ID,
                DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION,
                    DataBaseConstants.SPENDING.COLUMNS.VALUE,
                    DataBaseConstants.SPENDING.COLUMNS.DATE,
                    DataBaseConstants.SPENDING.COLUMNS.CURRENCY
                        )

            val cursor = db.query(
                    DataBaseConstants.SPENDING.TABLE_NAME,
                   projection, null, null, null, null, null
                        )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.ID))
                       val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION))
                        val value = cursor.getDouble(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.VALUE))
                        val date = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.DATE))
                        val currency = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.CURRENCY))

                        val spending = Spending(id, description, value, date, currency)
                        teste += spending.value!!
                    }
            }
            cursor?.close()
            teste
       }catch (e: Exception) {
            teste
       }
   }

    fun getSumCurrency(currencyId: Int): Double {
        var sum : Double = 0.0
        return try {
            val db = mSpendingDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.SPENDING.COLUMNS.ID,
                DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION,
                DataBaseConstants.SPENDING.COLUMNS.VALUE,
                DataBaseConstants.SPENDING.COLUMNS.DATE,
                DataBaseConstants.SPENDING.COLUMNS.CURRENCY
            )

            val cursor = db.query(
                DataBaseConstants.SPENDING.TABLE_NAME,
                projection, null, null, null, null, null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.ID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.DESCRIPTION))
                    val value = cursor.getDouble(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.VALUE))
                    val date = cursor.getString(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.DATE))
                    val currency = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.SPENDING.COLUMNS.CURRENCY))

                    val spending = Spending(id, description, value, date, currency)
                    if(currencyId == spending.currency) {
                        sum += spending.value!!
                    }
                }
            }
            cursor?.close()
            sum
        }catch (e: Exception) {
            sum
        }
    }

}
