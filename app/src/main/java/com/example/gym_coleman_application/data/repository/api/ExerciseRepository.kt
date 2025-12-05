package com.example.gym_coleman_application.data.repository.api

import com.example.gym_coleman_application.data.api.ExerciseApi
import com.example.gym_coleman_application.data.model.api.ExerciseItem

class ExerciseRepository(private val api: ExerciseApi) {

    suspend fun getExercises(): List<ExerciseItem> {
        val response = api.getExercises()
        return response.results   // 👉 aquí viene la lista REAL de ejercicios
    }
}
