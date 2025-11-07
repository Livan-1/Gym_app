package com.example.gym_coleman_application.data.repository

import com.example.gym_coleman_application.data.dao.ProductoDao
import com.example.gym_coleman_application.data.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {

    suspend fun  insertarProducto(producto: Producto){
        productoDao.insertarProducto(producto)
    }

    fun obtenerProductos(): Flow<List<Producto>> {
        return productoDao.obtenerProductos()
    }
}