package com.example.gym_coleman_application.data.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Update
import com.example.gym_coleman_application.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Insert
    suspend fun insertarProducto(producto: Producto)

    @Query("SELECT * FROM productos")
    fun obtenerProductos(): Flow<List<Producto>>
    @Delete()
    suspend fun eliminarProducto(producto: Producto)
    @Update()
    suspend fun actualizarProducto(producto: Producto)
}