package com.example.salon.admin.masters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R
import com.example.salon.util.AdminNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView


class MasterActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var masterAdapter: MasterAdapter
    private lateinit var databaseMasterHelper: DatabaseMasterHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masters)
        val addMasterImageView: ImageView = findViewById(R.id.addMasterImageView)

        bottomNavigationView = findViewById(R.id.bottomAdminNavigationView)
        recyclerView = findViewById(R.id.masterRecyclerView)
        masterAdapter = MasterAdapter()
        databaseMasterHelper = DatabaseMasterHelper(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MasterActivity)
            adapter = masterAdapter
        }

        // Загрузка данных мастеров из базы данных и добавление их в адаптер
        val masters = databaseMasterHelper.getAllMasters()
        masterAdapter.setMasters(masters)

        AdminNavigationHelper.setupAdminNavigationView(this, bottomNavigationView, R.id.menu_masters)
        addMasterImageView.setOnClickListener {
            val intent = Intent(this, MasterEditActivity::class.java)
            // Получение id следующей карточки
            val nextId = databaseMasterHelper.getNextMasterId()
            intent.putExtra("masterId", nextId)
            startActivity(intent)
        }
    }

    // Метод для добавления нового мастера в базу данных
    private fun addMasterToDatabase(master: Master) {
        databaseMasterHelper.insertMaster(master)
        val masters = databaseMasterHelper.getAllMasters()
        masterAdapter.setMasters(masters)
    }
}