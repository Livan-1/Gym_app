package com.example.gym_coleman_application.ui.theme.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gym_coleman_application.data.repository.AuthRepository

class RegisterViewModel(
    private val repo: AuthRepository = AuthRepository()
): ViewModel() {

    var uiState by mutableStateOf(RegisterUiState())

    fun onUserChange(v: String) {
        uiState = uiState.copy(username = v, error = null)
    }

    fun onPassChange(v: String) {
        uiState = uiState.copy(password = v, error = null)
    }

    fun onConfirmPassChange(v: String) {
        uiState = uiState.copy(confirmPassword = v, error = null)
    }

    fun submit(onSuccess: () -> Unit) {
        val user = uiState.username.trim()
        val pass = uiState.password
        val confirm = uiState.confirmPassword

        if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            uiState = uiState.copy(error = "Complete todos los campos")
            return
        }
        if (pass != confirm) {
            uiState = uiState.copy(error = "Las contraseñas no coinciden")
            return
        }

        val ok = repo.register(user, pass)
        if (!ok) {
            uiState = uiState.copy(error = "El usuario ya existe")
        } else {
            uiState = uiState.copy(success = true)
            onSuccess()
        }
    }
}
