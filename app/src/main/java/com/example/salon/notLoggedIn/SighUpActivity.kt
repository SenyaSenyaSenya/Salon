package com.example.salon.notLoggedIn

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import com.example.salon.user.UserScreenActivity
import com.example.salon.util.DatabaseHelper
import com.example.salon.util.User

class SighUpActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signupTextView: TextView = findViewById(R.id.signupTextView)
        signupTextView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        databaseHelper = DatabaseHelper(this)
        val adminUser = User(
            username = "login",
            password = "password",
            firstName = "Admin",
            lastName = "",
            dateOfBirth = "",
            phoneNumber = "",
            photoUri = ""
        )
        databaseHelper.addUser(adminUser)
        val loginButton: Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            validateFields()
        }

        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun validateFields() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
        } else {
            login(email, password)
        }
    }

    private fun login(username: String, password: String) {
        val isLoggedIn = databaseHelper.checkLogin(username, password)

        if (isLoggedIn) {
            val intent = Intent(this, UserScreenActivity::class.java)
            intent.putExtra("username", username) // передача имени пользователя
            startActivity(intent)
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
        }
    }
}