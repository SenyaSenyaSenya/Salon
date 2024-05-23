package com.example.salon.admin.masters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.salon.R
import com.example.salon.admin.services.DatabaseServiceHelper

class MasterEditActivity : AppCompatActivity() {
    private lateinit var specialtyAutoCompleteTextView: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_edit)

        val masterId = intent.getIntExtra("masterId", 1)

        specialtyAutoCompleteTextView = findViewById(R.id.masterSpecialtyAutoCompleteTextView)

        val dbServiceHelper = DatabaseServiceHelper(this)
        val specialties = dbServiceHelper.getAllServiceNames()
        specialtyAutoCompleteTextView.setDropDownBackgroundResource(android.R.color.white)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, specialties)
        specialtyAutoCompleteTextView.setAdapter(adapter)
        specialtyAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedSpecialty = parent.getItemAtPosition(position) as String
            // Выполните необходимые действия с выбранной специальностью
        }
    }
}