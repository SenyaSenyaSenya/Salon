package com.example.salon.util.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.salon.util.User

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "users"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_FIRST_NAME = "first_name"
        private const val COLUMN_LAST_NAME = "last_name"
        private const val COLUMN_DATE_OF_BIRTH = "date_of_birth"
        private const val COLUMN_PHONE_NUMBER = "phone_number"
        private const val COLUMN_PHOTO_URI = "photo_uri"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_USERNAME TEXT PRIMARY KEY, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_FIRST_NAME TEXT, " +
                "$COLUMN_LAST_NAME TEXT, " +
                "$COLUMN_DATE_OF_BIRTH TEXT, " +
                "$COLUMN_PHONE_NUMBER TEXT, " +
                "$COLUMN_PHOTO_URI TEXT" +
                ")"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Если вы хотите выполнить какие-либо операции при обновлении базы данных, добавьте их здесь
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(user: User) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD, user.password)
            put(COLUMN_FIRST_NAME, user.firstName)
            put(COLUMN_LAST_NAME, user.lastName)
            put(COLUMN_DATE_OF_BIRTH, user.dateOfBirth)
            put(COLUMN_PHONE_NUMBER, user.phoneNumber)
            put(COLUMN_PHOTO_URI, user.photoUri)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    private fun getUser(username: String, password: String): User? {
        val db = readableDatabase

        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val user: User? = if (cursor.moveToFirst()) {
            val columnIndexUsername = cursor.getColumnIndex(COLUMN_USERNAME)
            val columnIndexPassword = cursor.getColumnIndex(COLUMN_PASSWORD)
            val columnIndexFirstName = cursor.getColumnIndex(COLUMN_FIRST_NAME)
            val columnIndexLastName = cursor.getColumnIndex(COLUMN_LAST_NAME)
            val columnIndexDateOfBirth = cursor.getColumnIndex(COLUMN_DATE_OF_BIRTH)
            val columnIndexPhoneNumber = cursor.getColumnIndex(COLUMN_PHONE_NUMBER)
            val columnIndexPhotoUri = cursor.getColumnIndex(COLUMN_PHOTO_URI)

            if (columnIndexUsername >= 0) {
                User(
                    username = cursor.getString(columnIndexUsername),
                    password = cursor.getString(columnIndexPassword),
                    firstName = cursor.getString(columnIndexFirstName),
                    lastName = cursor.getString(columnIndexLastName),
                    dateOfBirth = cursor.getString(columnIndexDateOfBirth),
                    phoneNumber = cursor.getString(columnIndexPhoneNumber),
                    photoUri = cursor.getString(columnIndexPhotoUri)
                )
            } else {
                null
            }
        } else {
            null
        }
        cursor.close()
        db.close()
        return user
    }
    fun checkLogin(username: String, password: String): Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            return false  // Логин или пароль пустые
        }

        val user = getUser(username, password)
        return user?.password == password
    }
}