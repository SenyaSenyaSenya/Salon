package com.example.salon.notLoggedIn

import com.example.salon.user.UserScreenActivity
import android.content.Intent
import com.example.salon.util.PasswordUtils
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import com.example.salon.util.User
import com.example.salon.util.db.DatabaseHelper

class RegistrationActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordVisibilityImageView: ImageView
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        databaseHelper = DatabaseHelper(this)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordVisibilityImageView = findViewById(R.id.passwordVisibilityImageView)

        setupCreateAccountButton()
        setupPasswordEditText()
    }

    private fun setupCreateAccountButton() {
        val createAccountButton: Button = findViewById(R.id.registerButton)
        createAccountButton.setOnClickListener {
            validateFields()
        }
    }

    private fun setupPasswordEditText() {
        passwordVisibilityImageView.visibility = View.GONE // Скрываем иконку глаза при запуске активности

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                PasswordUtils.updatePasswordVisibilityIcon(
                    passwordEditText,
                    passwordVisibilityImageView
                )
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()

        passwordVisibilityImageView.setOnClickListener {
            PasswordUtils.togglePasswordVisibility(passwordEditText, passwordVisibilityImageView)
        }
    }

    private fun validateFields() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
        } else {
            val newUser = User(email, password, null, null, null, null, null)
            databaseHelper.addUser(newUser)

            // Переход на профиль пользователя и передача логина в качестве параметра
            val intent = Intent(this, UserScreenActivity::class.java)
            intent.putExtra("username", email)
            startActivity(intent)
            finish()
        }
    }
}