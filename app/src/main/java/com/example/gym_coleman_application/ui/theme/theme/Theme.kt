package com.example.gym_coleman_application.ui.theme.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Paleta para el tema oscuro (puedes dejarla o personalizarla)
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// --- APLICAMOS LA NUEVA PALETA AZUL Y BLANCO ---
private val LightColorScheme = lightColorScheme(
    // Color Primario: El color principal de tu marca (botones, barras, etc.)
    primary = AzulGym,

    // Color de Fondo: El fondo general de la app.
    background = BlancoPuro,

    // Superficie: El color de las 'Cards' y otros elementos elevados.
    // Lo ponemos blanco también para que se integre con el fondo.
    surface = BlancoPuro,

    // Color del contenido (texto/iconos) SOBRE el color primario.
    onPrimary = BlancoPuro,

    // Color del contenido SOBRE el fondo.
    onBackground = NegroTexto,

    // Color del contenido SOBRE las superficies (como las Cards).
    onSurface = NegroTexto,
)

@Composable
fun Gym_coleman_applicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // El color dinámico está disponible en Android 12+
    dynamicColor: Boolean = false, //  Recomendado ponerlo en 'false' para forzar mis colores
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
