package com.example.appbiblioteca.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appbiblioteca.viewmodel.AuthViewModel
import com.example.appbiblioteca.viewmodel.LibraryViewModel

@Composable
fun MyRentalsScreen(
    authViewModel: AuthViewModel,
    libraryViewModel: LibraryViewModel
) {
    val currentUser = authViewModel.currentUser.value
    val currentUserId = currentUser?.id
    val rentedBooks = if (currentUserId != null) {
        libraryViewModel.getRentedBooksForUser(currentUserId)
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cărțile mele închiriate",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (rentedBooks.isEmpty()) {
            Text(
                text = "Nu ai nicio carte închiriată momentan.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(rentedBooks) { book ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text("Autor: ${book.author}")
                            Text("Gen: ${book.genre}")

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    if (currentUserId != null) {
                                        libraryViewModel.returnBook(book.id, currentUserId)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Returnează")
                            }
                        }
                    }
                }
            }
        }
    }
}