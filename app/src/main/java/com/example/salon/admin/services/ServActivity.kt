package com.example.salon.admin.services

import Service
import ServiceAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R
import com.example.salon.util.AdminNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class ServActivity : AppCompatActivity() {
    private lateinit var serviceRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var addServiceImageView: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var serviceAdapter: ServiceAdapter
    private lateinit var serviceList: ArrayList<Service>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serv)

        bottomNavigationView = findViewById(R.id.bottomAdminNavigationView)
        AdminNavigationHelper.setupAdminNavigationView(this, bottomNavigationView, R.id.menu_service)

        serviceRecyclerView = findViewById(R.id.serviceRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        addServiceImageView = findViewById(R.id.addServiceImageView)

        // Инициализируем список услуг
        serviceList = ArrayList()

        // Создаем адаптер
        serviceAdapter = ServiceAdapter(serviceList)

        // Настраиваем RecyclerView
        serviceRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ServActivity)
            adapter = serviceAdapter
        }

        // Добавляем обработчик нажатия на кнопку добавления услуги
        addServiceImageView.setOnClickListener {
            // Создаем интент для перехода на com.example.salon.admin.services.EditServiceActivity и передаем пустые значения полей
            val intent = Intent(this@ServActivity, EditServiceActivity::class.java)
            intent.putExtra("serviceName", "")
            intent.putExtra("serviceDescription", "")
            intent.putExtra("serviceDuration", "")
            intent.putExtra("servicePrice", "")
            startActivity(intent)
        }
    }
}