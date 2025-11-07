package com.example.gym_coleman_application.data.repository

import com.example.gym_coleman_application.data.model.Credential

class AuthRepository (    private val validCredential: Credential =Credential.Admin
) {
    fun login(username: String, password: String): Boolean {
        return username == validCredential.username && password == validCredential.password
    }
}