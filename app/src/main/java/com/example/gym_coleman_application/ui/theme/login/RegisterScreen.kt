package com.example.gym_coleman_application.ui.theme.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    onRegisterSuccess: (String) -> Unit,     // 👉 AGREGADO
    vm: RegisterViewModel = viewModel()
) {

    val state = vm.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = state.username,
            onValueChange = { vm.onUserChange(it) },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { vm.onPassChange(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = { vm.onConfirmPassChange(it) },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        if (state.error != null) {
            Text(state.error!!, color = Color.Red)
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                vm.submit {
                    onRegisterSuccess(state.username)   // ✔ PERFECTO
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarme")
        }


        TextButton(onClick = { navController.navigate("login") }) {
            Text("Ya tengo cuenta, iniciar sesión")
        }
    }
}
