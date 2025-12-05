package com.example.gym_coleman_application.ui.theme.qr

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.gym_coleman_application.utils.QrCodeAnalyzer
import java.util.concurrent.Executors

@Composable
fun QrScannerScreen(
    onQrScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Variable para guardar la referencia a la cámara y poder controlarla (Zoom)
    var camera by remember { mutableStateOf<Camera?>(null) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCameraPermission = granted }
    )

    LaunchedEffect(key1 = true) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (hasCameraPermission) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val imageAnalysis = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()

                        imageAnalysis.setAnalyzer(
                            ContextCompat.getMainExecutor(ctx),
                            QrCodeAnalyzer { result -> onQrScanned(result) }
                        )

                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            cameraProvider.unbindAll()

                            // AQUÍ GUARDAMOS LA REFERENCIA A 'camera' PARA EL ZOOM
                            camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageAnalysis
                            )
                        } catch (e: Exception) {
                            Log.e("QrScanner", "Fallo al iniciar cámara", e)
                        }
                    }, ContextCompat.getMainExecutor(ctx))

                    previewView
                },
                // --- DETECTOR DE GESTOS (ZOOM) ---
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, _, zoomChange, _ ->
                            camera?.let { cam ->
                                // 1. Obtenemos el nivel de zoom actual
                                val currentZoom = cam.cameraInfo.zoomState.value?.zoomRatio ?: 1f

                                // 2. Calculamos el nuevo zoom multiplicando por el cambio del gesto
                                val newZoom = currentZoom * zoomChange

                                // 3. Aplicamos el zoom a la cámara (CameraControl)
                                cam.cameraControl.setZoomRatio(newZoom)
                            }
                        }
                    }
            )

            // --- UI SUPERPUESTA ---
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .border(4.dp, Color(0xFFFFD700), RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Pellizca para hacer Zoom", // Instrucción visual
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        } else {
            Text("Falta permiso de cámara", color = Color.White)
        }
    }
}