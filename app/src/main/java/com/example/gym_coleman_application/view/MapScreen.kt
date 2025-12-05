package com.example.gym_coleman_application.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gym_coleman_application.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("MissingPermission")
@Composable
fun MapScreen() {

    // 📍 Centro inicial → Santiago
    val santiago = LatLng(-33.4489, -70.6693)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(santiago, 12f)
    }

    // ⭐ Lista de gimnasios que quieres mostrar
    val gyms = listOf(
        GymLocation("SmartFit Plaza Vespucio", LatLng(-33.5226, -70.5986)),
        GymLocation("Energy Fitness Ñuñoa", LatLng(-33.4569, -70.5980)),
        GymLocation("Pacific Fitness Santiago Centro", LatLng(-33.4499, -70.6561)),
        GymLocation("Gimnasio DUOC UC San Joaquín", LatLng(-33.4945, -70.6136)),
        GymLocation("Sportlife La Florida", LatLng(-33.5201, -70.6052)),
        GymLocation("Sportlife Plaza Egaña", LatLng(-33.4632, -70.5811)),
        GymLocation("O2 Fit Centro", LatLng(-33.4513, -70.6602))
    )

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {

        // ⭐ Marcador central Coleman
        Marker(
            state = MarkerState(position = santiago),
            title = "Gimnasio Coleman (Central)",
            snippet = "Sede principal",
            icon = bitmapDescriptorFromRes(R.drawable.coleman, size = 90)
        )

        // ⭐ Marcadores de gimnasios
        gyms.forEach { gym ->
            Marker(
                state = MarkerState(position = gym.position),
                title = gym.name,
                snippet = "Gimnasio disponible"
            )
        }
    }
}

// Modelo simple
data class GymLocation(
    val name: String,
    val position: LatLng
)
