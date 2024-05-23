package com.example.salon.admin.news

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseNewsHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "news.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NEWS = "news"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DATE = "date"

        private const val TABLE_IMAGES = "images"
        private const val COLUMN_NEWS_ID = "news_id"
        private const val COLUMN_IMAGE_URL = "image_url"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createNewsTableQuery = "CREATE TABLE $TABLE_NEWS ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_DATE TEXT)"
        db.execSQL(createNewsTableQuery)

        val createImagesTableQuery = "CREATE TABLE $TABLE_IMAGES ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NEWS_ID INTEGER, $COLUMN_IMAGE_URL TEXT)"
        db.execSQL(createImagesTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NEWS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_IMAGES")
        onCreate(db)
    }

    fun insertNews(newsList: List<News>) {
        val db = writableDatabase

        for (news in newsList) {
            val values = ContentValues().apply {
                put(COLUMN_TITLE, news.title)
                put(COLUMN_DESCRIPTION, news.description)
                put(COLUMN_DATE, news.date)
            }
            val newRowId = db.insert(TABLE_NEWS, null, values)

            // Проверка на успешное добавление новости
            if (newRowId == -1L) {
                // Обработка ошибки добавления новости
            }

            // Добавление изображений новости, если они есть
            for (imageUrl in news.imageUrls) {
                val imageValues = ContentValues().apply {
                    put(COLUMN_NEWS_ID, newRowId)
                    put(COLUMN_IMAGE_URL, imageUrl)
                }
                db.insert(TABLE_IMAGES, null, imageValues)
            }
        }

        db.close()
    }

    fun updateNews(news: News) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TITLE, news.title)
            put(COLUMN_DESCRIPTION, news.description)
            put(COLUMN_DATE, news.date)
        }
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(news.id.toString())

        val rowsAffected = db.update(TABLE_NEWS, values, selection, selectionArgs)

        // Проверка на успешное обновление новости
        if (rowsAffected == 0) {
            // Обработка ошибки обновления новости
        }

        db.close()
    }

    fun deleteNews(news: News) {
        val db = writableDatabase

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(news.id.toString())

        val rowsDeleted = db.delete(TABLE_NEWS, selection, selectionArgs)

        // Проверка на успешное удаление новости
        if (rowsDeleted == 0) {
            // Обработка ошибки удаления новости
        }

        db.close()
    }

    @SuppressLint("Range")
    fun getAllNewsTitles(): List<String> {
        val newsTitles = mutableListOf<String>()

        val db = readableDatabase
        val cursor = db.query(TABLE_NEWS, arrayOf(COLUMN_TITLE), null, null, null, null, null)

        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            newsTitles.add(title)
        }

        cursor.close()
        db.close()

        return newsTitles
    }

    @SuppressLint("Range")
    fun getAllNews(): List<News> {
        val newsList = mutableListOf<News>()

        val db = readableDatabase
        val cursor = db.query(TABLE_NEWS, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
            val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))

            // Получение изображений новости, если они есть
            val imageUrls = getImageUrlsForNews(db, id)

            val news: News = News(id, title, description, imageUrls, date)
            newsList.add(news)
        }

        cursor.close()
        db.close()

        return newsList
    }
    @SuppressLint("Range")
    fun getNewsById(newsId: Int): News? {
        var news: News? = null

        val db = readableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(newsId.toString())

        val cursor = db.query(TABLE_NEWS, null, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
            val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))

            // Получение изображений новости, если они есть
            val imageUrls = getImageUrlsForNews(db, id)

            news = News(id, title, description, imageUrls, date)
        }

        cursor.close()
        db.close()

        return news
    }
    @SuppressLint("Range")
    private fun getImageUrlsForNews(db: SQLiteDatabase, newsId: Int): List<String> {
        val imageUrls = mutableListOf<String>()

        val selection = "$COLUMN_NEWS_ID = ?"
        val selectionArgs = arrayOf(newsId.toString())

        val cursor = db.query(TABLE_IMAGES, arrayOf(COLUMN_IMAGE_URL), selection, selectionArgs, null, null, null)

        while (cursor.moveToNext()) {
            val imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL))
            imageUrls.add(imageUrl)
        }

        cursor.close()

        return imageUrls
    }
}