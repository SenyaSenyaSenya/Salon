package com.example.salon.admin.services

import Service
import ServiceAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R
import android.widget.EditText
import android.widget.ImageView
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
        serviceList.add(Service("Название 1", "Описание 1", "Продолжительность 1", "Стоимость 1"))
        serviceList.add(Service("Название 2", "Описание 2", "Продолжительность 2", "Стоимость 2"))

        // Создаем адаптер
        serviceAdapter = ServiceAdapter(serviceList)

        // Настраиваем RecyclerView
        serviceRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ServActivity)
            adapter = serviceAdapter
        }

        // Добавляем обработчик нажатия на кнопку добавления услуги
        addServiceImageView.setOnClickListener {
            val name = searchEditText.text.toString()
            val description = "Описание"
            val duration = "Продолжительность"
            val price = "Стоимость"

            // Добавляем новую услугу в список
            serviceList.add(Service(name, description, duration, price))

            // Обновляем адаптер
            serviceAdapter.notifyDataSetChanged()

            // Очищаем поле ввода
            searchEditText.text.clear()
        }
    }
}