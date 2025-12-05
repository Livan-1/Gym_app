plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


// Agregar esto para habilitar KAPT

    kotlin("kapt")

}

android {
    namespace = "com.example.gym_coleman_application"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gym_coleman_application"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation("androidx.compose.foundation:foundation:1.6.7")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    // Dependencia para la navegación con Jetpack Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

// Íconos (core opcional) y EXTENDIDOS (¡este es el clave!)
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    // Google Maps Compose
    implementation("com.google.maps.android:maps-compose:2.11.4")

// Google Play Services Maps
    implementation("com.google.android.gms:play-services-maps:18.1.0")

// Places API (para buscar gimnasios después)
    implementation("com.google.android.libraries.places:places:3.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

// Gson Converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// OkHttp (opcional pero recomendado)
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")



    // Dependencias Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)  // Versión actualizada
    kapt("androidx.room:room-compiler:2.6.1")          // Misma versión
    implementation("androidx.room:room-ktx:2.6.1")     // Misma versión



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}