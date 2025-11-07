package com.example.gym_coleman_application.data.model

import android.media.Image
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName ="Productos")
data class Producto(
    @PrimaryKey(true)
    val id: Int=0,
    val nombre:String,
    val precio:String,
    val cantidad:String,
    val direccion:String,
    @DrawableRes val imagen: Int
    )