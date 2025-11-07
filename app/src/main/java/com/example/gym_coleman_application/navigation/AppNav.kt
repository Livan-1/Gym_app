package com.example.gym_coleman_application.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym_coleman_application.ui.theme.login.LoginScreen
import com.example.gym_coleman_application.view.ProductoFormScreen

import com.example.gym_coleman_application.R
import com.example.gym_coleman_application.view.DrawerMenu


// en este archivo defini toda la navegacion La aplicación inicia en la pantalla de login,
// y al iniciar sesión correctamente, navega al menú principal con la ruta de drawermenu $username

@Composable
fun AppNav() {
    val navController = rememberNavController()
    // Cuando el usuario inicia sesión correctamente, lo envío al DrawerMenu.
    // Uso un parámetro dinámico {username} para mostrar su nombre en la siguiente vista
    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { username ->
                    //// Esta instrucción borra el login del historial para que no se pueda volver atrás
                    navController.navigate("DrawerMenu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        //// 🔹 PANTALLA DEL MENÚ PRINCIPAL (DRAWER)
        //     Recibe el nombre del usuario como argumento.
        //      Ejemplo de ruta: DrawerMenu/Jorge

        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            /// Recupero el nombre que se pasó desde el login
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            //// Llamo a la pantalla DrawerMenu, enviándole el nombre y el controlador de navegación
            DrawerMenu(username = username, navController = navController)
        }

        ////  PANTALLA DE FORMULARIO DE PRODUCTO
        //  Aquí se muestran los detalles del producto seleccionado desde la lista.
        //    Recibe tres parámetros: nombre, precio e imagen (de tipo Int)
        //  Ruta con IMAGEN
        composable(
            route = "ProductoFormScreen/{nombre}/{precio}/{imagen}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType },
                navArgument("imagen") { type = NavType.IntType }
            )
            // Recupero los argumentos enviados desde ProductListScreen
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            val imagen = backStackEntry.arguments?.getInt("imagen") ?: R.drawable.creatina

            // Llamo al formulario del producto, pasándole los datos recuperados
            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imagen = imagen
            )
        }
    }
}
