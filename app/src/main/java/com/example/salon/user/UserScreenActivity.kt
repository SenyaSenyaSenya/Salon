package com.example.salon.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import com.example.salon.util.BottomNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserScreenActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_screen)
        @Suppress("DEPRECATION")
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        supportActionBar?.hide()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHelper.setupBottomNavigationView(this, bottomNavigationView, R.id.navigation_home)
        val username = intent.getStringExtra("username")
    }
}