package com.example.salon.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.salon.R
import com.example.salon.util.BottomNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class TimetableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        @Suppress("DEPRECATION")
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        val username = intent.getStringExtra("username")

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        username?.let {
            BottomNavigationHelper.setupBottomNavigationView(this, bottomNavigationView, R.id.navigation_timetable,
                it
            )
        }
    }
}