package com.example.appbiblioteca.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appbiblioteca.model.UserRole
import com.example.appbiblioteca.viewmodel.AuthViewModel
import com.example.appbiblioteca.viewmodel.LibraryViewModel

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    libraryViewModel: LibraryViewModel,
    onLogout: () -> Unit
) {
    val currentUser = authViewModel.currentUser.value
    val rentedBooksCount = currentUser?.id?.let {
        libraryViewModel.getRentedBooksForUser(it).size
    } ?: 0

    val roleText = when (currentUser?.role) {
        UserRole.ADMIN -> "Administrator"
        UserRole.USER -> "Utilizator"
        null -> "-"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Profil utilizator",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Nume: ${currentUser?.name ?: "-"}")
        Text("Email: ${currentUser?.email ?: "-"}")
        Text("Rol: $roleText")

        if (currentUser?.role == UserRole.USER) {
            Text("Cărți închiriate: $rentedBooksCount")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                authViewModel.logout()
                onLogout()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}