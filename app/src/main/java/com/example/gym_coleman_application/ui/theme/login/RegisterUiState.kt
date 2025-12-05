package com.example.gym_coleman_application.ui.theme.login

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val error: String? = null,
    val success: Boolean = false
)
