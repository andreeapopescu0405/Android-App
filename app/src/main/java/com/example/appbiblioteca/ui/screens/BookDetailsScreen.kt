package com.example.appbiblioteca.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appbiblioteca.model.UserRole
import com.example.appbiblioteca.viewmodel.AuthViewModel
import com.example.appbiblioteca.viewmodel.LibraryViewModel

@Composable
fun BookDetailsScreen(
    authViewModel: AuthViewModel,
    libraryViewModel: LibraryViewModel,
    onBack: () -> Unit,
    onEditBook: () -> Unit
) {
    val book = libraryViewModel.selectedBook.value
    val currentUser = authViewModel.currentUser.value
    val isAdmin = currentUser?.role == UserRole.ADMIN
    val currentUserId = currentUser?.id
    val isRentedByCurrentUser = book?.rentedByUserId == currentUserId

    if (book == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nu a fost selectată nicio carte.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Înapoi")
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Autor: ${book.author}")
        Text("Gen: ${book.genre}")

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Descriere: ${book.description}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (book.available) "Status: Disponibilă" else "Status: Indisponibilă",
            color = if (book.available) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.error
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isAdmin) {
            Button(
                onClick = onEditBook,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editează carte")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    libraryViewModel.deleteBook(book.id)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Șterge carte")
            }
        } else {
            if (book.available && currentUserId != null) {
                Button(
                    onClick = {
                        libraryViewModel.rentBook(book.id, currentUserId)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Închiriază")
                }
            } else if (isRentedByCurrentUser && currentUserId != null) {
                Button(
                    onClick = {
                        libraryViewModel.returnBook(book.id, currentUserId)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Returnează")
                }
            } else {
                OutlinedButton(
                    onClick = { },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Indisponibilă")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Înapoi")
        }
    }
}