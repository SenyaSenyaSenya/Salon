package com.example.salon.admin.news

data class News(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
    val date: String
)