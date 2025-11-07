package com.example.gym_coleman_application.view

// Importaciones necesarias para animaciones, UI y navegación
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym_coleman_application.R
import kotlinx.coroutines.launch

// --------------------------------------------------------------------------------------
// 🎯 OBJETIVO DEL ARCHIVO:
// Este archivo define el menú lateral de la aplicación (Drawer).
// Incluye animaciones de fondo, una pesa en movimiento y transiciones visuales para
// las opciones del menú, además de la navegación entre pantallas.
// --------------------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(username: String, navController: NavController) {

    // ------------------------------------------------------------------
    // 🔹 CONTROL DEL ESTADO DEL DRAWER
    // 'drawerState' controla si el menú está abierto o cerrado.
    // 'scope' se usa para ejecutar acciones dentro de corrutinas.
    // ------------------------------------------------------------------
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // ------------------------------------------------------------------
    // 🌈 FONDO ANIMADO CON DEGRADADO
    // El fondo se anima constantemente para dar sensación de energía y movimiento.
    // Cambia su posición verticalmente con una transición infinita.
    // ------------------------------------------------------------------
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val animatedBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0047AB), // Azul principal
            Color(0xFF0A1931), // Azul profundo
            Color(0xFF00111F)  // Azul casi negro
        ),
        startY = animatedOffset,
        endY = animatedOffset + 1000f
    )

    // ------------------------------------------------------------------
    // 🏋️ ANIMACIÓN DE LA PESA
    // Esta animación da vida al menú, simbolizando el concepto del gimnasio.
    // La pesa sube y baja constantemente mientras rota suavemente.
    // ------------------------------------------------------------------
    val weightTransition = rememberInfiniteTransition(label = "")
    val offsetY by weightTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    val rotation by weightTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // ------------------------------------------------------------------
    // 🧭 ESTRUCTURA PRINCIPAL DEL MENÚ LATERAL
    // Se compone de dos partes:
    // - drawerContent → El contenido del menú (animado)
    // - contenido principal → La pantalla que se muestra detrás
    // ------------------------------------------------------------------
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Transparent, // Fondo transparente
                modifier = Modifier
                    .fillMaxHeight()
                    .background(animatedBrush) // Aplico el degradado animado
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // ----------------------------------------------------------
                // 🏋️ PESA ANIMADA ENCIMA DEL LOGO
                // Aparece centrada y con efecto de rotación y desplazamiento.
                // ----------------------------------------------------------
                Image(
                    painter = painterResource(id = R.drawable.pesa),
                    contentDescription = "Pesa animada",
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.CenterHorizontally)
                        .offset(y = offsetY.dp)
                        .rotate(rotation)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ----------------------------------------------------------
                // 🔤 TÍTULOS DEL MENÚ
                // Muestra el nombre del gimnasio y un saludo personalizado
                // con el nombre del usuario (recibido desde AppNav).
                // ----------------------------------------------------------
                Text(
                    text = "GYM Coleman",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

                Text(
                    text = "Bienvenido, $username",
                    color = Color(0xFFFFD700), // Dorado motivacional
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Divider(
                    color = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // ----------------------------------------------------------
                // 🧾 LISTA DE OPCIONES DEL MENÚ
                // Cada opción aparece con una animación progresiva (fade + slide).
                // ----------------------------------------------------------
                val items = listOf(
                    "Inicio" to "🏠",
                    "Productos" to "💪",
                    "Entrenamientos" to "🔥",
                    "Cerrar sesión" to "🚪"
                )

                items.forEachIndexed { index, pair ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(300, delayMillis = index * 150)) +
                                slideInHorizontally(initialOffsetX = { -100 })
                    ) {
                        DrawerItem(
                            text = pair.first,
                            icon = pair.second,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    when (pair.first) {
                                        "Inicio" -> navController.navigate("inicio")
                                        "Productos" -> navController.navigate("product_list")
                                        "Entrenamientos" -> navController.navigate("trainings")
                                        "Cerrar sesión" -> {
                                            navController.navigate("login") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        // ------------------------------------------------------------------
        // 🏬 CONTENIDO PRINCIPAL (la lista de productos)
        // Se muestra detrás del menú. Al abrir el Drawer, esta vista se desplaza.
        // ------------------------------------------------------------------
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GYM Coleman", color = Color(0xFF0047AB)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "Menú",
                                tint = Color(0xFF0047AB)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.TopCenter
            ) {
                // Llamo a la pantalla de productos
                ProductListScreen(navController)
            }
        }
    }
}

// ------------------------------------------------------------------
// 🧩 COMPONENTE REUTILIZABLE PARA CADA OPCIÓN DEL MENÚ
// Muestra el texto con su ícono y ejecuta la acción al hacer clic.
// ------------------------------------------------------------------
@Composable
fun DrawerItem(text: String, icon: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$icon  $text",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
