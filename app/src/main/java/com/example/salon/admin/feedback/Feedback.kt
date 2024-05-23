package com.example.salon.admin.feedback

import Service
import com.example.salon.admin.clients.Client
import com.example.salon.admin.masters.Master
import com.example.salon.admin.schedule.Schedule

data class Feedback(
    val id: Int,
    val author: Client,
    val procedure: Service,
    val schedule: Schedule,
    val review: String,
    val master: Master,
    val rating: Int
)