package com.example.gym_coleman_application.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym_coleman_application.R

// --- IMPORTS DE TUS PANTALLAS ---
import com.example.gym_coleman_application.ui.theme.login.LoginScreen
import com.example.gym_coleman_application.ui.theme.login.RegisterScreen
import com.example.gym_coleman_application.ui.theme.qr.QrScannerScreen // <--- Tu Scanner
import com.example.gym_coleman_application.view.DrawerMenu
import com.example.gym_coleman_application.view.ProductoFormScreen
import com.example.gym_coleman_application.view.MapScreen
import com.example.gym_coleman_application.ui.theme.home.ExerciseScreen

@Composable
fun AppNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // 🔹 LOGIN (Corregido)
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { username ->
                    // Al loguearse, vamos al menú y borramos el login del historial
                    navController.navigate("DrawerMenu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    // AQUÍ ESTABA TU ERROR ANTES: Ahora sí le pasamos la acción
                    navController.navigate("register")
                }
            )
        }

        // 🔹 REGISTRO
        composable("register") {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = { username ->
                    navController.navigate("DrawerMenu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 🔹 DRAWER (Menú Principal)
        composable(
            "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username = username, navController = navController)
        }

        // 📷 ESCÁNER QR (Tu nueva funcionalidad)
        composable("qr_scanner") {
            QrScannerScreen(
                onQrScanned = { codigoLeido ->
                    // Acción al detectar un QR
                    println("QR LEÍDO: $codigoLeido")

                    // Por ahora, volvemos atrás al escanear.
                    // Si quisieras ir a un producto, podrías hacer un 'if' aquí.
                    navController.popBackStack()
                }
            )
        }

        // 🔹 FORMULARIO PRODUCTO
        composable(
            "ProductoFormScreen/{nombre}/{precio}/{imagen}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType },
                navArgument("imagen") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            val imagen = backStackEntry.arguments?.getInt("imagen") ?: R.drawable.creatina

            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imagen = imagen
            )
        }

        // 🔹 MAPA
        composable("mapa") {
            MapScreen()
        }

        // ⭐ ENTRENAMIENTOS
        composable("trainings") {
            ExerciseScreen()
        }
    }
}