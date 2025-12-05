package com.example.gym_coleman_application.ui.theme.login

import android.Manifest
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gym_coleman_application.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (username: String) -> Unit,
    onRegisterClick: () -> Unit
) {
    val state = viewModel.uiState
    val context = LocalContext.current // Necesario para mostrar mensajes (Toast)

    // 1. LANZADOR DE CÁMARA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            viewModel.onPhotoTaken(bitmap)
        }
    }

    // 2. LANZADOR DE PERMISOS (NUEVO)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Si da permiso, intentamos abrir la cámara
            try {
                cameraLauncher.launch(null)
            } catch (e: Exception) {
                Toast.makeText(context, "Error: No se encontró app de cámara", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Se requiere permiso de cámara", Toast.LENGTH_SHORT).show()
        }
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0047AB), Color(0xFF0A1931), Color.Black)
    )

    // Animaciones
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -15f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing), RepeatMode.Reverse), label = ""
    )
    val rotation by infiniteTransition.animateFloat(
        initialValue = -10f, targetValue = 10f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing), RepeatMode.Reverse), label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize().background(brush = gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // IMAGENES
            Image(
                painter = painterResource(id = R.drawable.duocuc),
                contentDescription = "Duoc",
                modifier = Modifier.size(150.dp).offset(y = offsetY.dp).rotate(rotation)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.planes),
                contentDescription = "Coleman",
                modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("GYM COLEMAN", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Entrena. Supera. Inspira.", color = Color(0xFFFFD700), fontSize = 16.sp, modifier = Modifier.padding(bottom = 20.dp))

            // --- CÁMARA ---
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                IconButton(
                    onClick = {
                        // AL HACER CLICK, PRIMERO PEDIMOS PERMISO
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    modifier = Modifier.background(Color(0xFFFFD700), CircleShape).size(50.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, "Foto", tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(16.dp))
                if (state.capturedImage != null) {
                    Image(
                        bitmap = state.capturedImage.asImageBitmap(), contentDescription = "Foto",
                        modifier = Modifier.size(60.dp).clip(CircleShape).border(2.dp, Color(0xFFFFD700), CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(Color.Gray.copy(alpha = 0.5f)).border(2.dp, Color.White, CircleShape))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // INPUTS
            OutlinedTextField(
                value = state.username,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Usuario", color = Color.White) },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700), unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color(0xFFFFD700), unfocusedLabelColor = Color.White,
                    focusedTextColor = Color.White, unfocusedTextColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña", color = Color.White) },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700), unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color(0xFFFFD700), unfocusedLabelColor = Color.White,
                    focusedTextColor = Color.White, unfocusedTextColor = Color.White
                )
            )

            AnimatedVisibility(state.error != null) {
                Text(state.error ?: "", color = Color(0xFFFF6B6B), modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÓN INGRESAR
            Button(
                onClick = { viewModel.submit(onLoginSuccess) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                } else {
                    Text("INGRESAR", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿No tienes cuenta? Regístrate aquí",
                color = Color.White,
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
    }
}