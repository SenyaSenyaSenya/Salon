package com.example.salon.util

import com.example.salon.admin.services.ServActivity
import android.app.Activity
import android.content.Intent
import com.example.salon.R
import com.example.salon.admin.clients.ClientsActivity
import com.example.salon.admin.news.NewsActivity
import com.example.salon.admin.masters.MasterActivity
import com.example.salon.admin.schedule.ScheduleActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
object AdminNavigationHelper {
    fun setupAdminNavigationView(activity: Activity, bottomNavigationView: BottomNavigationView?, currentScreen: Int) {
        bottomNavigationView?.selectedItemId = currentScreen

        @Suppress("DEPRECATION")
        bottomNavigationView?.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_clients -> {
                    if (currentScreen != R.id.menu_clients) {
                        val intent = Intent(activity, ClientsActivity::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.menu_timetable -> {
                    if (currentScreen != R.id.menu_timetable) {
                        val intent = Intent(activity, ScheduleActivity::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.menu_service -> {
                    if (currentScreen != R.id.menu_service) {
                        val intent = Intent(activity, ServActivity::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.menu_masters -> {
                    if (currentScreen != R.id.menu_masters) {
                        val intent = Intent(activity, MasterActivity::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.menu_discount -> {
                    if (currentScreen != R.id.menu_discount) {
                        val intent = Intent(activity, NewsActivity::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                else -> false
            }
        }
    }
}