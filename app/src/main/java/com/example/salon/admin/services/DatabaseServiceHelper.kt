package com.example.salon.admin.services

import Service
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseServiceHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "service_database"
        private const val TABLE_SERVICES = "services"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DURATION = "duration"
        private const val COLUMN_PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery =
            "CREATE TABLE IF NOT EXISTS $TABLE_SERVICES ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_DURATION TEXT, $COLUMN_PRICE TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SERVICES")
        onCreate(db)
    }

    fun createService(name: String, description: String, duration: String, price: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_DESCRIPTION, description)
        values.put(COLUMN_DURATION, duration)
        values.put(COLUMN_PRICE, price)
        val id = db.insert(TABLE_SERVICES, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllServices(): List<Service> {
        val services = mutableListOf<Service>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_SERVICES,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_PRICE),
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val service = Service(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
            )
            services.add(service)
        }

        cursor.close()
        db.close()

        return services
    }

    @SuppressLint("Range")
    fun getServiceById(id: Int): Service? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_SERVICES,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_PRICE),
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        val service: Service? = if (cursor.moveToFirst()) {
            Service(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
            )
        } else {
            null
        }

        cursor.close()
        db.close()

        return service
    }

    fun updateService(id: Int, name: String, description: String, duration: String, price: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_DESCRIPTION, description)
        values.put(COLUMN_DURATION, duration)
        values.put(COLUMN_PRICE, price)
        db.update(TABLE_SERVICES, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteService(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_SERVICES, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun searchServices(query: String): List<Service> {
        val services = mutableListOf<Service>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_SERVICES,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_PRICE),
            "$COLUMN_NAME LIKE ? OR $COLUMN_DESCRIPTION LIKE ? OR $COLUMN_DURATION LIKE ? OR $COLUMN_PRICE LIKE ?",
            arrayOf("%$query%", "%$query%", "%$query%", "%$query%"),
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val service = Service(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
            )
            services.add(service)
        }

        cursor.close()
        db.close()

        return services
    }

    @SuppressLint("Range")
    fun getAllServiceNames(): List<String> {
        val serviceNames = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_SERVICES,
            arrayOf(COLUMN_NAME),
            null,
            null,
            null,
            null,
            "$COLUMN_NAME ASC"
        )

        while (cursor.moveToNext()) {
            val serviceName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            serviceNames.add(serviceName)
        }

        cursor.close()
        db.close()

        return serviceNames
    }

}