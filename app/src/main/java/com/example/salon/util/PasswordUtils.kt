package com.example.salon.util

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.example.salon.R

object PasswordUtils {
    fun togglePasswordVisibility(
        passwordEditText: EditText,
        passwordVisibilityImageView: ImageView
    ) {
        val isPasswordVisible = passwordEditText.transformationMethod is HideReturnsTransformationMethod

        if (isPasswordVisible) {
            passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordVisibilityImageView.setImageResource(R.drawable.icon_visibility_off)
        } else {
            passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordVisibilityImageView.setImageResource(R.drawable.icon_visibility)
        }

        updatePasswordVisibilityIcon(passwordEditText, passwordVisibilityImageView)
    }

    fun updatePasswordVisibilityIcon(
        passwordEditText: EditText,
        passwordVisibilityImageView: ImageView
    ) {
        if (passwordEditText.text.isNotEmpty()) {
            passwordVisibilityImageView.visibility = View.VISIBLE
            if (passwordEditText.transformationMethod is HideReturnsTransformationMethod) {
                passwordVisibilityImageView.setImageResource(R.drawable.icon_visibility)
            } else {
                passwordVisibilityImageView.setImageResource(R.drawable.icon_visibility_off)
            }
        } else {
            passwordVisibilityImageView.visibility = View.GONE
        }
    }
}