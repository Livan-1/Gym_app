package com.example.gym_coleman_application.ui.theme.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gym_coleman_application.R
import androidx.compose.animation.core.*
import androidx.compose.ui.text.TextStyle


// Este archivo define la pantalla de LOGIN de la aplicación.
// Aquí se maneja la autenticación inicial, la animación del fondo y del logo, y la validación
// de usuario/contraseña antes de entrar al menú principal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: (username: String) -> Unit
) {

    //VARIABLES DE ESTADO
    //  Uso 'remember' para guardar los valores del usuario, contraseña y error.
    //  Estas variables se actualizan en tiempo real mientras el usuario escribe

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    // FONDO CON DEGRADADO
    //   Combino tres tonos (azul, azul oscuro y negro) para dar un efecto profesional
    //   y mantener la identidad visual del GYM COLEMAN
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0047AB), // Azul intenso
            Color(0xFF0A1931), // Azul oscuro
            Color.Black
        )
    )

    // ⚙ Animación del logo duoc
    // La imagen se mueve constantemente hacia arriba y abajo, con una leve rotación.
    // Esto da una sensación de movimiento y energía en la pantalla de inicio
    val infiniteTransition = rememberInfiniteTransition(label = "")
    //// Movimiento vertical (sube y baja)
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    // Rotación suave (balanceo de lado a lado)
    val rotation by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // ESTRUCTURA PRINCIPAL: UN BOX CON UN COLUMN AL CENTRO
    // El Box contiene todo el fondo, y el Column organiza los elementos verticalmente
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //  PESA ANIMADA O LOGO PRINCIPAL
            // Este elemento da movimiento a la pantalla, reforzando el concepto fitness
            Image(
                painter = painterResource(id = R.drawable.duocuc),
                contentDescription = "Duoc animado",
                modifier = Modifier
                    .size(300.dp)
                    .offset(y = offsetY.dp)
                    .rotate(rotation)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // LOGO DEL GIMNASIO (con animación de aparición)
            // Aparece con una animación suave de entrada (fade + escala)
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + scaleIn(initialScale = 0.7f),
                exit = scaleOut(targetScale = 0.8f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.planes),
                    contentDescription = "Logo Gimnasio Coleman",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // TÍTULOS Y SUBTÍTULO
            // Refuerzan la identidad visual del gimnasio.

            Text(
                text = "GYM COLEMAN",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Entrena. Supera. Inspira.",
                color = Color(0xFFFFD700),
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )


            //CAMPO DE USUARIO
            // Campo con texto blanco, borde dorado y validación reactiva
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario", color = Color.White) },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700),
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color(0xFFFFD700),
                    unfocusedLabelColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔒 CAMPO DE CONTRASEÑA
            // Igual formato que el campo de usuario, pero con visualTransformation
            // para ocultar los caracteres ingresados
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color.White) },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700),
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color(0xFFFFD700),
                    unfocusedLabelColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            // ⚠️ MENSAJE DE ERROR
            // Aparece solo si las credenciales son incorrectas
            AnimatedVisibility(showError) {
                Text(
                    "Usuario o contraseña incorrectos",
                    color = Color(0xFFFF6B6B),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            // 🚀 BOTÓN DE INGRESO
            // Al presionar, se validan los campos. Si son correctos, se navega al menú principal
            Button(
                onClick = {
                    //// Validación de credenciales simples
                    if (username == "usuario" && password == "1234") {
                        showError = false
                        onLoginSuccess(username)
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
            ) {
                Text(
                    text = "INGRESAR",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}
