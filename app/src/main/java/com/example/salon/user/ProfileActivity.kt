package com.example.salon.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.salon.R
import com.example.salon.util.BottomNavigationHelper
import com.example.salon.util.db.DatabaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var lastNameTextView: TextView
    private lateinit var firstNameTextView: TextView
    private lateinit var dateOfBirthTextView: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var username: String
    private lateinit var imageViewEdit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        @Suppress("DEPRECATION")
        databaseHelper = DatabaseHelper(this)
        val username = intent.getStringExtra("username")
        val user = username?.let { databaseHelper.getUser(it) }
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        username?.let {
            BottomNavigationHelper.setupBottomNavigationView(
                this, bottomNavigationView, R.id.navigation_profile,
                it
            )
        }

        imageViewEdit = findViewById<ImageView>(R.id.imageViewEdit)
        imageViewEdit.visibility = View.GONE

        firstNameTextView = findViewById<TextView>(R.id.textViewFirstName)
        lastNameTextView = findViewById<TextView>(R.id.textViewLastName)
        dateOfBirthTextView = findViewById<TextView>(R.id.textViewDateOfBirth)
        phoneNumberTextView = findViewById<TextView>(R.id.textViewPhone)

        if (user != null) {
            if (!user.firstName.isNullOrEmpty()) {
                firstNameTextView.text = user.firstName
            }
            if (!user.lastName.isNullOrEmpty()) {
                lastNameTextView.text = user.lastName
            }
            if (!user.dateOfBirth.isNullOrEmpty()) {
                dateOfBirthTextView.text = user.dateOfBirth
            }
            if (!user.phoneNumber.isNullOrEmpty()) {
                phoneNumberTextView.text = user.phoneNumber
            }
        }

        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Не нужно ничего делать перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // При изменении текста, делаем галочку видимой
                imageViewEdit.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                // Не нужно ничего делать после изменения текста
            }
        }

        firstNameTextView.addTextChangedListener(textChangedListener)
        lastNameTextView.addTextChangedListener(textChangedListener)
        dateOfBirthTextView.addTextChangedListener(textChangedListener)
        phoneNumberTextView.addTextChangedListener(textChangedListener)

        imageViewEdit.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Вы хотите изменить информацию?")
            .setPositiveButton("Да") { dialog, _ ->
                updateUserInfo()
                dialog.dismiss()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun updateUserInfo() {
        val newFirstName = firstNameTextView.text.toString()
        val newLastName = lastNameTextView.text.toString()
        val newDateOfBirth = dateOfBirthTextView.text.toString()
        val newPhoneNumber = phoneNumberTextView.text.toString()

        // Проверка на пустое значение или null и обновление соответствующих полей в базе данных
        if (!newFirstName.isNullOrEmpty()) {
            databaseHelper.updateFirstName(username, newFirstName)
        }
        if (!newLastName.isNullOrEmpty()) {
            databaseHelper.updateLastName(username, newLastName)
        }
        if (!newDateOfBirth.isNullOrEmpty()) {
            databaseHelper.updateDateOfBirth(username, newDateOfBirth)
        }
        if (!newPhoneNumber.isNullOrEmpty()) {
            databaseHelper.updatePhoneNumber(username, newPhoneNumber)
        }
    }
}