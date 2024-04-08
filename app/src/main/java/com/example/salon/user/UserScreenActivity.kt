package com.example.salon.user

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserScreenActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_screen)
        supportActionBar?.hide()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHelper.setupBottomNavigationView(this, bottomNavigationView, R.id.navigation_home)

        // Получение переданного имени пользователя
        val username = intent.getStringExtra("username")

        // Дальнейшая обработка полученной информации
        // Например, отображение имени пользователя в TextView

    }
}