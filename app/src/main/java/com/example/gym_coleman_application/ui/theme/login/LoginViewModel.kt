package com.example.gym_coleman_application.ui.theme.login


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

    fun submit(onSuccess: (String) -> Unit) {
        uiState = uiState.copy(isLoading = true, error = null)

        val ok = repo.login(
            user = uiState.username.trim(),
            pass = uiState.password
        )

        if (ok) {
            onSuccess(uiState.username.trim())
        } else {
            uiState = uiState.copy(error = "Credenciales inválidas")
        }

        uiState = uiState.copy(isLoading = false)
    }
}
