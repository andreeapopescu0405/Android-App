package com.example.appbiblioteca.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.appbiblioteca.model.User
import com.example.appbiblioteca.model.UserRole

class AuthViewModel : ViewModel() {

    private val _users = mutableStateOf(
        mutableListOf(
            User(
                id = 1,
                name = "Administrator",
                email = "admin@biblioteca.com",
                password = "admin123",
                role = UserRole.ADMIN
            )
        )
    )

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _authError = mutableStateOf<String?>(null)
    val authError: State<String?> = _authError

    fun register(
        name: String,
        email: String,
        password: String
    ): Boolean {
        val cleanName = name.trim()
        val cleanEmail = email.trim().lowercase()
        val cleanPassword = password.trim()

        if (cleanName.isEmpty() || cleanEmail.isEmpty() || cleanPassword.isEmpty()) {
            _authError.value = "Completează toate câmpurile."
            return false
        }

        if (_users.value.any { it.email == cleanEmail }) {
            _authError.value = "Există deja un cont cu acest email."
            return false
        }

        val newId = (_users.value.maxOfOrNull { it.id } ?: 0) + 1

        val newUser = User(
            id = newId,
            name = cleanName,
            email = cleanEmail,
            password = cleanPassword,
            role = UserRole.USER
        )

        _users.value.add(newUser)
        _authError.value = null
        return true
    }

    fun login(
        email: String,
        password: String
    ): Boolean {
        val cleanEmail = email.trim().lowercase()
        val cleanPassword = password.trim()

        if (cleanEmail.isEmpty() || cleanPassword.isEmpty()) {
            _authError.value = "Completează emailul și parola."
            return false
        }

        val user = _users.value.find {
            it.email == cleanEmail && it.password == cleanPassword
        }

        return if (user != null) {
            _currentUser.value = user
            _isLoggedIn.value = true
            _authError.value = null
            true
        } else {
            _authError.value = "Email sau parolă incorecte."
            false
        }
    }

    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value = false
        _authError.value = null
    }

    fun clearError() {
        _authError.value = null
    }
}