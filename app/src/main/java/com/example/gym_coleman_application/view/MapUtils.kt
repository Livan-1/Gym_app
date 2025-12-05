package com.example.gym_coleman_application.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
fun bitmapDescriptorFromRes(resId: Int, size: Int = 90): BitmapDescriptor {
    val context = LocalContext.current
    val original = BitmapFactory.decodeResource(context.resources, resId)

    // Escalar icono a 64x64 o el tamaño que quieras
    val scaled = Bitmap.createScaledBitmap(original, size, size, false)

    return BitmapDescriptorFactory.fromBitmap(scaled)
}
