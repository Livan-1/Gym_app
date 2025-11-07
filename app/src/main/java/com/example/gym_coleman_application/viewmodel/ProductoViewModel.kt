package com.example.gym_coleman_application.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gym_coleman_application.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel: ViewModel(){

    private val _producto = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _producto.asStateFlow()

    fun guardarProducto(producto: Producto){
        viewModelScope.launch {
            // guardar en memoria
            val nuevaLista = _producto.value + producto
            _producto.value = nuevaLista

        }// fin Scope
    } // fin guardarProducto
} //     fin viewmodel