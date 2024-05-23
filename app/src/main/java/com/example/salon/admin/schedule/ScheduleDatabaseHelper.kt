package com.example.salon.admin.schedule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ScheduleDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_SCHEDULE (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_DATETIME TEXT," +
                "$COLUMN_MASTER_ID INTEGER," +
                "$COLUMN_SERVICE_ID INTEGER," +
                "$COLUMN_CLIENT_ID INTEGER" +
                ")"

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Выполните необходимые действия при обновлении версии базы данных
    }

    companion object {
        private const val DATABASE_NAME = "schedule.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_SCHEDULE = "schedule"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DATETIME = "datetime"
        private const val COLUMN_MASTER_ID = "master_id"
        private const val COLUMN_SERVICE_ID = "service_id"
        private const val COLUMN_CLIENT_ID = "client_id"
    }
}