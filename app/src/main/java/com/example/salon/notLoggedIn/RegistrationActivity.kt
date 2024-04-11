package com.example.salon.notLoggedIn

import com.example.salon.util.PasswordUtils
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R

class RegistrationActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordVisibilityImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

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
            // Регистрация пользователя
        }
    }
}