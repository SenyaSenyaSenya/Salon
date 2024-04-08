package com.example.salon.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.salon.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHelper.setupBottomNavigationView(this, bottomNavigationView, R.id.navigation_profile)
    }
}