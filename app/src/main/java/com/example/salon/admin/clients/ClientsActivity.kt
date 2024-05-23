package com.example.salon.admin.clients

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R
import com.example.salon.util.AdminNavigationHelper
import com.example.salon.util.db.DatabaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import android.os.Bundle as Bundle1

class ClientsActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        bottomNavigationView = findViewById(R.id.bottomAdminNavigationView)
        AdminNavigationHelper.setupAdminNavigationView(
            this,
            bottomNavigationView,
            R.id.menu_clients
        )
        val recyclerViewClients = findViewById<RecyclerView>(R.id.recyclerViewClients)
        val dbHelper = DatabaseHelper(this)
        val userList = dbHelper.getAllUsers()
        val adapter = ClientAdapter(userList)
        recyclerViewClients.adapter = adapter
    }
}