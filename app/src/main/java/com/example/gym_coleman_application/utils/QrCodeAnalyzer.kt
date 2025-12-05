package com.example.gym_coleman_application.utils


import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrCodeAnalyzer(
    private val onQrDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {

    // Configuración del scanner para leer QR y Aztec
    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()
    )

    // La anotación @OptIn es obligatoria para usar imageProxy.image
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            // Preparamos la imagen para ML Kit
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            // Procesamos la imagen buscando códigos
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    // Si encontramos códigos, recorremos la lista
                    for (barcode in barcodes) {
                        barcode.rawValue?.let { qrCodeValue ->
                            // Enviamos el valor encontrado a la pantalla
                            onQrDetected(qrCodeValue)
                        }
                    }
                }
                .addOnFailureListener {
                    // Si falla, no hacemos nada para que siga intentando
                }
                .addOnCompleteListener {
                    // ¡MUY IMPORTANTE! Cerramos la imagen para liberar memoria
                    imageProxy.close()
                }
        } else {
            // Si la imagen venía vacía, cerramos igual
            imageProxy.close()
        }
    }
}