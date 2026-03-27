package com.example.appbiblioteca.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BookDetailsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("1984", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Autor: George Orwell")
        Text("Gen: Dystopian")
        Spacer(modifier = Modifier.height(12.dp))
        Text("Descriere: O carte despre supraveghere, putere și libertate.")
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Închiriază")
        }
    }
}