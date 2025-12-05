package com.example.gym_coleman_application.view

// Importaciones necesarias para animaciones, listas, ViewModel y navegación
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.gym_coleman_application.R
import com.example.gym_coleman_application.data.model.Producto
import com.example.gym_coleman_application.viewmodel.ProductoViewModel

// --------------------------------------------------------------------------------
// 🎯 OBJETIVO DEL ARCHIVO:
// Mostrar el detalle del producto seleccionado y permitir al usuario
// realizar un pedido llenando un formulario con cantidad, dirección y forma de pago.
// --------------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    imagen: Int
) {
    // ---------------------------------------------------------------------------
    // 📋 VARIABLES DE ESTADO
    // Se usan para capturar lo que el usuario escribe o selecciona.
    // ---------------------------------------------------------------------------
    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }

    var Efectivo by remember { mutableStateOf(false) }
    var Debito by remember { mutableStateOf(false) }

    // ---------------------------------------------------------------------------
    // 🧠 VIEWMODEL
    // Se utiliza para guardar los pedidos confirmados y mantenerlos en memoria.
    // ---------------------------------------------------------------------------
    val viewModel: ProductoViewModel = viewModel()
    val productos: List<Producto> by viewModel.productos.collectAsState()

    // ---------------------------------------------------------------------------
    // 🧭 ESTRUCTURA PRINCIPAL — Scaffold
    // Permite manejar zonas fijas como TopBar, Content y BottomBar.
    // Aquí usamos BottomAppBar vacía para mantener consistencia visual.
    // ---------------------------------------------------------------------------
    Scaffold(
        bottomBar = { BottomAppBar { } }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // -------------------------------------------------------------------
            // 🎬 ANIMACIÓN DE ENTRADA DE LA IMAGEN
            // Aparece con efecto de fade + escala, dando una transición elegante.
            // -------------------------------------------------------------------
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { visible = true }

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + scaleIn(initialScale = 0.8f),
                exit = fadeOut() + scaleOut(targetScale = 1.2f)
            ) {
                Image(
                    painter = painterResource(id = imagen),
                    contentDescription = nombre,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // -------------------------------------------------------------------
            // 🧱 TÍTULO Y PRECIO DEL PRODUCTO
            // -------------------------------------------------------------------
            Text(text = nombre, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Precio: $precio", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // -------------------------------------------------------------------
            // ✏️ CAMPOS DE TEXTO
            // El usuario ingresa cantidad y dirección para el pedido.
            // -------------------------------------------------------------------
            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // -------------------------------------------------------------------
            // 💳 MÉTODOS DE PAGO
            // El usuario puede elegir entre pago en efectivo o con débito.
            // -------------------------------------------------------------------
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = Efectivo, onCheckedChange = { Efectivo = it })
                Text("Pago en Efectivo")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = Debito, onCheckedChange = { Debito = it })
                Text("Pago con Débito")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // -------------------------------------------------------------------
            // 🚀 BOTÓN CONFIRMAR PEDIDO
            // Valida que los campos no estén vacíos antes de guardar el pedido.
            // -------------------------------------------------------------------
            Button(
                onClick = {
                    val producto = Producto(
                        nombre = nombre,
                        precio = precio,
                        cantidad = cantidad.text,
                        direccion = direccion.text,
                        imagen = imagen
                    )
                    viewModel.guardarProducto(producto)
                },
                enabled = cantidad.text.isNotBlank() && direccion.text.isNotBlank()
            ) {
                Text("Confirmar Pedido")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // -------------------------------------------------------------------
            // 📦 LISTA DE PEDIDOS GUARDADOS
            // Muestra todos los productos que el usuario ha pedido.
            // -------------------------------------------------------------------
            Text("Pedidos realizados:", style = MaterialTheme.typography.headlineSmall)

            if (productos.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(productos) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = producto.imagen),
                                    contentDescription = producto.nombre,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(end = 10.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Column {
                                    Text(
                                        text = "${producto.nombre} - ${producto.precio}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Cantidad: ${producto.cantidad}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Dirección: ${producto.direccion}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // Mensaje si no hay pedidos aún
                Text(
                    text = "No hay pedidos realizados",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    ProductoFormScreen(
        navController = rememberNavController(),
        nombre = "Proteína Whey",
        precio = "60.000",
        imagen = R.drawable.proteina
    )
}
