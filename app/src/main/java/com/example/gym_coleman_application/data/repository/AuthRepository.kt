package com.example.gym_coleman_application.data.repository

class AuthRepository {

    // 🔐 LOGIN SIMPLE HARDCODEADO
    fun login(user: String, pass: String): Boolean {
        return user == "usuario" && pass == "1234"
    }

    // 🔐 REGISTRO SIMPLE (para luego mejorarlo con BD)
    fun register(user: String, pass: String): Boolean {
        return user.isNotBlank() && pass.isNotBlank()
    }
}
