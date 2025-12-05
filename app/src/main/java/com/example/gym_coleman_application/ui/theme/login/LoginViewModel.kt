package com.example.gym_coleman_application.ui.theme.login

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gym_coleman_application.data.repository.AuthRepository

class LoginViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value, error = null)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null)
    }

    // --- LÓGICA DE LA CÁMARA (ESTO TE FALTA) ---
    fun onPhotoTaken(bitmap: Bitmap) {
        val pixelated = createPixelatedBitmap(bitmap)
        uiState = uiState.copy(capturedImage = pixelated)
    }

    private fun createPixelatedBitmap(original: Bitmap): Bitmap {
        val smallWidth = 20
        val smallHeight = (original.height.toFloat() / original.width.toFloat() * smallWidth).toInt()
        val smallBitmap = Bitmap.createScaledBitmap(original, smallWidth, smallHeight, false)
        return Bitmap.createScaledBitmap(smallBitmap, 300, 300, false)
    }
    // -------------------------------------------

    fun submit(onSuccess: (String) -> Unit) {
        uiState = uiState.copy(isLoading = true, error = null)
        val oK = repo.login(uiState.username.trim(), uiState.password)
        if (oK) {
            onSuccess(uiState.username.trim())
            uiState = uiState.copy(isLoading = false)
        } else {
            uiState = uiState.copy(error = "Credenciales inválidas", isLoading = false)
        }
    }
}