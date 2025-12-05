package com.example.gym_coleman_application.ui.theme.login

import android.graphics.Bitmap // <--- Importante que esté este import

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val capturedImage: Bitmap? = null // <--- ESTO ES LO QUE TE FALTA
)