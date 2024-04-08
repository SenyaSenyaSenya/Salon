package com.example.salon.notLoggedIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.salon.R

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoImageView = findViewById<ImageView>(R.id.imageSplash)
        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        logoImageView.startAnimation(scaleAnimation)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, SighUpActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}