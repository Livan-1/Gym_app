package com.example.gym_coleman_application.ui.theme.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gym_coleman_application.viewmodel.ExerciseViewModel

@Composable
fun ExerciseScreen(vm: ExerciseViewModel = viewModel()) {

    val exercises = vm.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        vm.loadExercises()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(exercises) { item ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Column(Modifier.padding(16.dp)) {

                    Text(
                        text = "Ejercicio: ${item.name}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Descripción: ${item.description ?: "Sin descripción"}"
                    )

                }
            }
        }
    }
}


