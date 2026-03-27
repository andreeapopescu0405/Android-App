package com.example.appbiblioteca.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Profil utilizator", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Nume: Popescu Ana")
        Text("Email: ana.popescu@email.com")
        Text("Cărți închiriate: 2")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
            Text("Editează profilul")
        }
    }
}