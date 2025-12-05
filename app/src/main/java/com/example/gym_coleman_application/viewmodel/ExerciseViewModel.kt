package com.example.gym_coleman_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_coleman_application.data.api.RetrofitClient
import com.example.gym_coleman_application.data.api.ExerciseApi
import com.example.gym_coleman_application.data.repository.api.ExerciseRepository
import com.example.gym_coleman_application.data.model.api.ExerciseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {

    private val api = RetrofitClient.retrofit.create(ExerciseApi::class.java)
    private val repo = ExerciseRepository(api)

    private val _uiState = MutableStateFlow<List<ExerciseItem>>(emptyList())
    val uiState: StateFlow<List<ExerciseItem>> = _uiState

    fun loadExercises() {
        viewModelScope.launch {
            try {
                val exercises = repo.getExercises()
                _uiState.value = exercises
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
