package com.example.gym_coleman_application.data.api

import com.example.gym_coleman_application.data.model.api.ExerciseResponse
import retrofit2.http.GET

interface ExerciseApi {

    @GET("exercise/")
    suspend fun getExercises(): ExerciseResponse
}
