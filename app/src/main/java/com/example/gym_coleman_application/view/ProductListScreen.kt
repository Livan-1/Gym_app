package com.example.gym_coleman_application.view

// Importaciones necesarias para animaciones, listas y navegación
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym_coleman_application.R

// ----------------------------------------------------------------------------------
// 🎯 OBJETIVO DEL ARCHIVO:
// Mostrar todos los productos del gimnasio con animaciones suaves y atractivas.
// Cada producto tiene su nombre, precio e imagen, y además una animación individual
// de entrada tipo "rebote" para hacerlo más interactivo y moderno.
// ----------------------------------------------------------------------------------

// 🧩 Estructura simple para los productos
data class ProductoSimple(
    val nombre: String,
    val precio: String,
    val imagen: Int
)

// ----------------------------------------------------------------------------------
// 🏬 FUNCIÓN PRINCIPAL DE LA PANTALLA
// Aquí se muestra la lista animada de productos con un banner superior.
// ----------------------------------------------------------------------------------
@Composable
fun ProductListScreen(navController: NavController) {

    // -------------------------------------------------------------------------
    // 📦 Lista de productos disponibles
    // Cada producto incluye: nombre, precio e imagen (guardada en drawable).
    // -------------------------------------------------------------------------
    val productosDisponibles = listOf(
        ProductoSimple("Creatina", "25.000", R.drawable.creatina),
        ProductoSimple("Proteína Whey", "60.000", R.drawable.proteina),
        ProductoSimple("Pre-entreno", "48.990", R.drawable.pre),
        ProductoSimple("Omega 3", "30.000", R.drawable.omega3),
        ProductoSimple("Barra de Proteína", "1.500", R.drawable.barraproteina),
        ProductoSimple("Shaker", "7.990", R.drawable.shaker)
    )

    // -------------------------------------------------------------------------
    // 🎬 Animación de entrada del banner principal
    // Aparece desde arriba con efecto fade y slide vertical.
    // -------------------------------------------------------------------------
    var showBanner by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showBanner = true
    }

    // -------------------------------------------------------------------------
    // 🧭 Estructura principal: LazyColumn
    // LazyColumn es una lista vertical eficiente que renderiza solo lo visible.
    // -------------------------------------------------------------------------
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        // -----------------------------------------------------
        // 🏋️ Banner superior del gimnasio
        // -----------------------------------------------------
        item {
            AnimatedVisibility(
                visible = showBanner,
                enter = fadeIn(animationSpec = tween(800)) +
                        slideInVertically(
                            initialOffsetY = { -80 },
                            animationSpec = tween(600)
                        )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.planes),
                    contentDescription = "Banner Gimnasio Coleman",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // -----------------------------------------------------
        // 🔹 Título de la sección
        // -----------------------------------------------------
        item {
            Text(
                text = "Nuestros Productos",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // -----------------------------------------------------
        // 🧩 Listado de productos (animados uno por uno)
        // Cada producto aparece con efecto fade + slide horizontal.
        // Además, tiene un pequeño rebote usando animateFloatAsState.
        // -----------------------------------------------------
        itemsIndexed(productosDisponibles) { index, producto ->

            // Controla si el producto ya está visible
            var visible by remember { mutableStateOf(false) }

            // Escala animada (efecto rebote al aparecer)
            val scale by animateFloatAsState(
                targetValue = if (visible) 1f else 0.9f,
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            )

            // Pequeño delay para que los productos entren uno tras otro
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(index * 120L)
                visible = true
            }

            // Animación de entrada de cada tarjeta
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(500)) +
                        slideInHorizontally(initialOffsetX = { 100 }),
            ) {

                // -------------------------------------------------
                // 💳 Tarjeta del producto
                // -------------------------------------------------
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .scale(scale) // efecto rebote
                        .clickable {
                            // Al hacer clic, navega al detalle del producto
                            navController.navigate(
                                "ProductoFormScreen/${producto.nombre}/${producto.precio}/${producto.imagen}"
                            )
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // 🖼️ Imagen del producto
                        Image(
                            painter = painterResource(id = producto.imagen),
                            contentDescription = producto.nombre,
                            modifier = Modifier.size(70.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // 🧾 Nombre y precio
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = producto.nombre,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "$${producto.precio}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
