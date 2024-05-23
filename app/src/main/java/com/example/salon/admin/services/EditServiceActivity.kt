package com.example.salon.admin.services

import Service
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import java.io.Serializable

class EditServiceActivity : AppCompatActivity() {
    private lateinit var service: Service
    private lateinit var databaseHelper: DatabaseServiceHelper
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_service)

        val serviceId = intent.getIntExtra("serviceId", 0)

        databaseHelper = DatabaseServiceHelper(this)

        // Проверяем, является ли это редактированием существующего элемента или добавлением нового
        isEditMode = serviceId != 0

        if (isEditMode) {
            // Если это редактирование, получаем запись из базы данных по ID
            service = databaseHelper.getServiceById(serviceId)!!
        } else {
            // Если это добавление нового элемента, создаем пустой объект Service
            service = Service(0, "", "", "", "")
        }

        val serviceNameEditText = findViewById<EditText>(R.id.serviceNameEditText)
        val serviceDescriptionEditText = findViewById<EditText>(R.id.serviceDescriptionEditText)
        val serviceDurationEditText = findViewById<EditText>(R.id.serviceDurationEditText)
        val servicePriceEditText = findViewById<EditText>(R.id.servicePriceEditText)
        serviceNameEditText.setText(service.name)
        serviceDescriptionEditText.setText(service.description)
        serviceDurationEditText.setText(service.duration)
        servicePriceEditText.setText(service.price)
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            saveService()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_service, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.saveServiceMenuItem -> {
                saveService()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveService() {
        val serviceNameEditText = findViewById<EditText>(R.id.serviceNameEditText)
        val serviceDescriptionEditText = findViewById<EditText>(R.id.serviceDescriptionEditText)
        val serviceDurationEditText = findViewById<EditText>(R.id.serviceDurationEditText)
        val servicePriceEditText = findViewById<EditText>(R.id.servicePriceEditText)
        val newName = serviceNameEditText.text.toString()
        val newDescription = serviceDescriptionEditText.text.toString()
        val newDuration = serviceDurationEditText.text.toString()
        val newPrice = servicePriceEditText.text.toString()
        if (isEditMode) {
            // Если это редактирование, обновляем запись в базе данных с использованием новых значений
            service = Service(service.id, newName, newDescription, newDuration, newPrice)
            databaseHelper.updateService(service.id, newName, newDescription, newDuration, newPrice)
        } else {
            // Если это добавление нового элемента, создаем новую запись в базе данных
            val newServiceId = databaseHelper.createService(newName, newDescription, newDuration, newPrice)
            service = Service(newServiceId.toInt(), newName, newDescription, newDuration, newPrice)
        }

        // Возвращаем данные обратно в вызывающую активность
        val returnIntent = Intent()
        returnIntent.putExtra("service", service as Parcelable)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}