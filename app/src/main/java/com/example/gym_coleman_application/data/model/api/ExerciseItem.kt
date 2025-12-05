package com.example.gym_coleman_application.data.model.api

import com.google.gson.annotations.SerializedName

data class ExerciseItem(
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("category")
    val category: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("difficulty")
    val difficulty: String
)
