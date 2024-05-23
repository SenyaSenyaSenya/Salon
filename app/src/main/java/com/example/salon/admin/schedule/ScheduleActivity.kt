package com.example.salon.admin.schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.salon.R
import com.example.salon.util.AdminNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class ScheduleActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        bottomNavigationView = findViewById(R.id.bottomAdminNavigationView)
        AdminNavigationHelper.setupAdminNavigationView(this, bottomNavigationView, R.id.menu_timetable)

    }
}