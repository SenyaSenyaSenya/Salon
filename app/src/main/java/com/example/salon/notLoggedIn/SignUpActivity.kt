package com.example.salon.notLoggedIn

import com.example.salon.util.PasswordUtils
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.salon.R
import com.example.salon.admin.ClientsActivity
import com.example.salon.user.UserScreenActivity
import com.example.salon.util.db.DatabaseHelper

class SignUpActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordVisibilityImageView: ImageView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        @Suppress("DEPRECATION")
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordVisibilityImageView = findViewById(R.id.passwordVisibilityImageView)
        databaseHelper = DatabaseHelper(this)

        setupSignUpTextView()
        setupLoginButton()
        setupPasswordEditText()
    }

    private fun setupSignUpTextView() {
        val signupTextView: TextView = findViewById(R.id.signupTextView)
        signupTextView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupLoginButton() {
        val loginButton: Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            validateFields()
        }
    }

    private fun setupPasswordEditText() {
        passwordVisibilityImageView.visibility =
            View.GONE // Скрываем иконку глаза при запуске активности

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
            login(email, password)
        }
    }

    private fun login(username: String, password: String) {
        val isLoggedIn = databaseHelper.checkLogin(username, password)
        if (username == "admin" && password == "admin") {
            val intent1 = Intent(this, ClientsActivity::class.java)
            startActivity(intent1)
        } else if (isLoggedIn) {
            val intent = Intent(this, UserScreenActivity::class.java)
            intent.putExtra("username", username) // передача имени пользователя
            startActivity(intent)
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
        }
    }
}