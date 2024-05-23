package com.example.salon.admin.schedule

import Service
import com.example.salon.admin.masters.Master

data class Schedule(
    val id: Int,
    val datetime: String,
    val master: Master,
    val service: Service,
    val clientId: Int?
)