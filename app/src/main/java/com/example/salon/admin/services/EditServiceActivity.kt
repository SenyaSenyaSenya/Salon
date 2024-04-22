package com.example.salon.admin.services

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import com.example.salon.database.DatabaseServiceHelper
import com.example.salon.util.db.DatabaseHelper

class EditServiceActivity : AppCompatActivity() {
    private lateinit var serviceName: String
    private lateinit var serviceDescription: String
    private lateinit var serviceDuration: String
    private lateinit var servicePrice: String

    private lateinit var databaseHelper: DatabaseServiceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_service)

        serviceName = intent.getStringExtra("serviceName") ?: ""
        serviceDescription = intent.getStringExtra("serviceDescription") ?: ""
        serviceDuration = intent.getStringExtra("serviceDuration") ?: ""
        servicePrice = intent.getStringExtra("servicePrice") ?: ""
        val serviceNameEditText = findViewById<EditText>(R.id.serviceNameEditText)
        val serviceDescriptionEditText = findViewById<EditText>(R.id.serviceDescriptionEditText)
        val serviceDurationEditText = findViewById<EditText>(R.id.serviceDurationEditText)
        val servicePriceEditText = findViewById<EditText>(R.id.servicePriceEditText)
        serviceNameEditText.setText(serviceName)
        serviceDescriptionEditText.setText(serviceDescription)
        serviceDurationEditText.setText(serviceDuration)
        servicePriceEditText.setText(servicePrice)

        databaseHelper = DatabaseServiceHelper(this)
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

        // Обновляем запись в базе данных с использованием новых значений newName, newDescription, newDuration и newPrice
        databaseHelper.updateService(serviceName, newName, newDescription, newDuration, newPrice)

        // Завершаем активность
        finish()
    }
}