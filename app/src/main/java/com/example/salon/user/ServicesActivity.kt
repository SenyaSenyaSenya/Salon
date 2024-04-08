package com.example.salon.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.salon.R

class ServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}