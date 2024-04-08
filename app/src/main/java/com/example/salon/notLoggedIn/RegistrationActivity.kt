package com.example.salon.notLoggedIn

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import com.example.salon.util.DatabaseHelper
import com.example.salon.util.User

class RegistrationActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        usernameEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        databaseHelper = DatabaseHelper(this)

        val registerButton: Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            registerUser()
        }

        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun registerUser() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
//        } else if (!isValidEmail(username)) {
//            Toast.makeText(this, "Введите корректный адрес электронной почты", Toast.LENGTH_SHORT).show()
        } else {
            val user = User(
                username = username,
                password = password,
                firstName = "",
                lastName = "",
                dateOfBirth = "",
                phoneNumber = "",
                photoUri = ""
            )
            databaseHelper.addUser(user)

            Toast.makeText(this, "Подтвердите регистрацию на почте", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

//    private fun isValidEmail(email: String): Boolean {
//        val pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
//        return email.matches(pattern.toRegex())
//    }
}