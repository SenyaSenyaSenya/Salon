package com.example.salon.user

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import com.example.salon.util.BottomNavigationHelper
import com.example.salon.util.db.DatabaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserScreenActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_screen)
        @Suppress("DEPRECATION")
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        supportActionBar?.hide()

        databaseHelper = DatabaseHelper(this)

        val username = intent.getStringExtra("username")
        val welcomeMessage = "Добро пожаловать, ${username?.let { getUserName(it) }}"
        Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        username?.let {
            BottomNavigationHelper.setupBottomNavigationView(this, bottomNavigationView, R.id.navigation_home,
                it
            )
        }
    }

    private fun getUserName(username: String): String {
        val user = databaseHelper.getUser(username)
        return if (user != null && !user.firstName.isNullOrEmpty()) {
            user.firstName
        } else {
            username
        }
    }
}