package com.example.salon.util

data class User(
    val username: String,
    val password: String,
    val firstName: String?,
    val lastName: String?,
    val dateOfBirth: String?,
    val phoneNumber: String?,
    val photoUri: String?
)