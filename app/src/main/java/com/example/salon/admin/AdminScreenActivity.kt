package com.example.salon.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.salon.R

class AdminScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_screen)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }
}