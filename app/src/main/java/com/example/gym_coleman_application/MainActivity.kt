package com.example.gym_coleman_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
// Importa tu archivo de navegación
import com.example.gym_coleman_application.navigation.AppNav
import com.example.gym_coleman_application.ui.theme.theme.Gym_coleman_applicationTheme

// Importa tu archivo de tema


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //  Envolvemos toda la aplicación en nuestro tema personalizado.
            //    Esto le da a todos los componentes los colores que definimos (azul, blanco, etc.).
            Gym_coleman_applicationTheme {

                // Usamos una 'Surface' que toma el color de fondo de nuestro tema.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //  Llamamos a AppNav(), que contiene toda la lógica de navegación.
                    //    MainActivity ya no necesita conocer las rutas, solo llama al navegador.
                    AppNav()
                }
            }
        }
    }
}
