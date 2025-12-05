package com.example.gym_coleman_application.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym_coleman_application.ui.theme.login.LoginScreen
import com.example.gym_coleman_application.view.ProductoFormScreen
import com.example.gym_coleman_application.view.DrawerMenu
import com.example.gym_coleman_application.view.MapScreen
import com.example.gym_coleman_application.ui.theme.login.RegisterScreen
import com.example.gym_coleman_application.R
import com.example.gym_coleman_application.ui.theme.home.ExerciseScreen


@Composable
fun AppNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // 🔹 LOGIN
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { username ->
                    navController.navigate("DrawerMenu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
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

        // 🔹 DRAWER
        composable(
            "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username = username, navController = navController)
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

        // ⭐ Entrenamientos
        composable("trainings") {
            ExerciseScreen()
        }


    }
}
