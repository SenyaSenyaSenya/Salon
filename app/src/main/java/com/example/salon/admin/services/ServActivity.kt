package com.example.salon.admin.services

import Service
import ServiceAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

    private lateinit var databaseHelper: DatabaseServiceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serv)

        bottomNavigationView = findViewById(R.id.bottomAdminNavigationView)
        AdminNavigationHelper.setupAdminNavigationView(
            this,
            bottomNavigationView,
            R.id.menu_service
        )

        serviceRecyclerView = findViewById(R.id.serviceRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        addServiceImageView = findViewById(R.id.addServiceImageView)

        setupRecyclerView()
        setupListeners()
        loadDataFromDatabase()
    }

    override fun onResume() {
        super.onResume()
        loadServiceData()
    }

    private fun setupRecyclerView() {
        serviceList = ArrayList()
        databaseHelper = DatabaseServiceHelper(this)
        serviceAdapter = ServiceAdapter(serviceList, databaseHelper)
        serviceRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ServActivity)
            adapter = serviceAdapter
        }
        serviceAdapter.setOnItemClickListener { service ->
            val intent = Intent(this@ServActivity, EditServiceActivity::class.java)
            intent.putExtra("serviceId", service.id)
            startActivity(intent)
        }
    }

    private fun setupListeners() {
        addServiceImageView.setOnClickListener {
            val newService = Service(0, "", "", "", "")
            val newServiceId = databaseHelper.createService(
                newService.name,
                newService.description,
                newService.duration,
                newService.price
            )

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    performSearch(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            val intent = Intent(this@ServActivity, EditServiceActivity::class.java)
            intent.putExtra("serviceId", newServiceId.toString())
            startActivity(intent)
        }
    }

    private fun loadDataFromDatabase() {
        loadServiceData()
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadServiceData() {
        serviceList.clear()
        serviceList.addAll(databaseHelper.getAllServices())
        serviceAdapter.notifyDataSetChanged()
    }

    private fun performSearch(query: String) {
        val searchResults = databaseHelper.searchServices(query)
        serviceList.clear()
        serviceList.addAll(searchResults)
        serviceAdapter.notifyDataSetChanged()
    }
}