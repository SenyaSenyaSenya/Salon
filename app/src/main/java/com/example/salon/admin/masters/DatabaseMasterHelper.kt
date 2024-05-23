package com.example.salon.admin.masters
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseMasterHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "masters.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_MASTERS = "masters"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EXPERIENCE = "experience"
        private const val COLUMN_SPECIALTY = "specialty"
        private const val COLUMN_ABOUT = "about"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_MASTERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_EXPERIENCE TEXT," +
                "$COLUMN_SPECIALTY TEXT," +
                "$COLUMN_ABOUT TEXT" +
                ")"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MASTERS")
        onCreate(db)
    }

    fun insertMaster(master: Master) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, master.name)
            put(COLUMN_EXPERIENCE, master.experience)
            put(COLUMN_SPECIALTY, master.specialty)
            put(COLUMN_ABOUT, master.about)
        }
        db.insert(TABLE_MASTERS, null, values)
        db.close()
    }
    fun getNextMasterId(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT MAX($COLUMN_ID) FROM $TABLE_MASTERS", null)
        var nextId = 1 // Значение по умолчанию, если таблица пустая
        if (cursor.moveToFirst()) {
            val maxId = cursor.getInt(0)
            nextId = maxId + 1
        }
        cursor.close()
        db.close()
        return nextId
    }
    @SuppressLint("Range")
    fun getAllMasters(): List<Master> {
        val masters = mutableListOf<Master>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_MASTERS,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_EXPERIENCE, COLUMN_SPECIALTY, COLUMN_ABOUT),
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val master = Master(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EXPERIENCE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_SPECIALTY)),
                cursor.getString(cursor.getColumnIndex(COLUMN_ABOUT))
            )
            masters.add(master)
        }

        cursor.close()
        db.close()

        return masters
    }
}