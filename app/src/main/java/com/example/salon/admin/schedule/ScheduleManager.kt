package com.example.salon.admin.schedule

import android.content.ContentValues
import android.content.Context
import com.example.salon.admin.clients.Client

class ScheduleManager(context: Context) {

    private val dbHelper: ScheduleDatabaseHelper = ScheduleDatabaseHelper(context)

    private companion object {
        private const val TABLE_SCHEDULE = "schedule"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DATETIME = "datetime"
        private const val COLUMN_MASTER_ID = "master_id"
        private const val COLUMN_SERVICE_ID = "service_id"
        private const val COLUMN_CLIENT_ID = "client_id"
    }

    fun addScheduleItem(scheduleItem: Schedule) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_DATETIME, scheduleItem.datetime)
            put(COLUMN_MASTER_ID, scheduleItem.master.id)
            put(COLUMN_SERVICE_ID, scheduleItem.service.id)
            put(COLUMN_CLIENT_ID, scheduleItem.clientId)
        }

        val newRowId = db.insert(TABLE_SCHEDULE, null, values)

        if (newRowId == -1L) {
            // Обработка ошибки добавления пункта расписания
        }

        db.close()
    }

    fun updateScheduleItem(scheduleItem: Schedule) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_DATETIME, scheduleItem.datetime)
            put(COLUMN_MASTER_ID, scheduleItem.master.id)
            put(COLUMN_SERVICE_ID, scheduleItem.service.id)
            put(COLUMN_CLIENT_ID, scheduleItem.clientId)
        }

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(scheduleItem.id.toString())

        val rowsAffected = db.update(TABLE_SCHEDULE, values, selection, selectionArgs)

        if (rowsAffected == 0) {
            // Обработка ошибки обновления пункта расписания
        }

        db.close()
    }

    fun deleteScheduleItem(scheduleItemId: Int) {
        val db = dbHelper.writableDatabase

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(scheduleItemId.toString())

        val rowsDeleted = db.delete(TABLE_SCHEDULE, selection, selectionArgs)

        if (rowsDeleted == 0) {
            // Обработка ошибки удаления пункта расписания
        }

        db.close()
    }

    fun bookScheduleItem(scheduleItemId: Int, client: Client) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_CLIENT_ID, client.id)
        }

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(scheduleItemId.toString())

        val rowsAffected = db.update(TABLE_SCHEDULE, values, selection, selectionArgs)

        if (rowsAffected == 0) {
            // Обработка ошибки бронирования пункта расписания
        }

        db.close()
    }

    fun cancelBooking(scheduleItemId: Int) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            remove(COLUMN_CLIENT_ID)
        }

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(scheduleItemId.toString())

        val rowsAffected = db.update(TABLE_SCHEDULE, values, selection, selectionArgs)

        if (rowsAffected == 0) {
            // Обработка ошибки отмены бронирования пункта расписания
        }

        db.close()
    }
}
