package com.example.salon.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseServiceHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "salon.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_SERVICES = "services"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DURATION = "duration"
        private const val COLUMN_PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createServicesTable = "CREATE TABLE $TABLE_SERVICES ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_DURATION TEXT, $COLUMN_PRICE TEXT)"
        db.execSQL(createServicesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SERVICES")
        onCreate(db)
    }

    fun updateService(oldName: String, newName: String, newDescription: String, newDuration: String, newPrice: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, newName)
        values.put(COLUMN_DESCRIPTION, newDescription)
        values.put(COLUMN_DURATION, newDuration)
        values.put(COLUMN_PRICE, newPrice)
        db.update(TABLE_SERVICES, values, "$COLUMN_NAME = ?", arrayOf(oldName))
        db.close()
    }
}