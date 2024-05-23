package com.example.salon.util

import com.example.salon.user.UserScreenActivity
import android.app.Activity
import android.content.Intent
import com.example.salon.R
import com.example.salon.user.ProfileActivity
import com.example.salon.user.ServicesActivity
import com.example.salon.user.TimetableActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
object BottomNavigationHelper {
        fun setupBottomNavigationView(activity: Activity, bottomNavigationView: BottomNavigationView?, currentScreen: Int, username: String) {
            bottomNavigationView?.selectedItemId = currentScreen

            @Suppress("DEPRECATION")
            bottomNavigationView?.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_home -> {
                        if (currentScreen != R.id.navigation_home) {
                            val intent = Intent(activity, UserScreenActivity::class.java)
                            intent.putExtra("username", username)
                            activity.startActivity(intent)
                        }
                        true
                    }
                    R.id.navigation_services -> {
                        if (currentScreen != R.id.navigation_services) {
                            val intent = Intent(activity, ServicesActivity::class.java)
                            intent.putExtra("username", username)
                            activity.startActivity(intent)
                        }
                        true
                    }
                    R.id.navigation_profile -> {
                        if (currentScreen != R.id.navigation_profile) {
                            val intent = Intent(activity, ProfileActivity::class.java)
                            intent.putExtra("username", username)
                            activity.startActivity(intent)
                        }
                        true
                    }
                    R.id.navigation_timetable -> {
                        if (currentScreen != R.id.navigation_timetable) {
                            val intent = Intent(activity, TimetableActivity::class.java)
                            intent.putExtra("username", username)
                            activity.startActivity(intent)
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }
