package com.example.appbiblioteca.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appbiblioteca.model.Book
import com.example.appbiblioteca.viewmodel.LibraryViewModel

@Composable
fun BookFormScreen(
    libraryViewModel: LibraryViewModel,
    isEditMode: Boolean,
    onSaveSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    val selectedBook: Book? = libraryViewModel.selectedBook.value

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isEditMode, selectedBook) {
        if (isEditMode && selectedBook != null) {
            title = selectedBook.title
            author = selectedBook.author
            genre = selectedBook.genre
            description = selectedBook.description
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = if (isEditMode) "Editează carte" else "Adaugă carte",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                errorMessage = null
            },
            label = { Text("Titlu") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = author,
            onValueChange = {
                author = it
                errorMessage = null
            },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = genre,
            onValueChange = {
                genre = it
                errorMessage = null
            },
            label = { Text("Gen") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                errorMessage = null
            },
            label = { Text("Descriere") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (
                    title.trim().isEmpty() ||
                    author.trim().isEmpty() ||
                    genre.trim().isEmpty() ||
                    description.trim().isEmpty()
                ) {
                    errorMessage = "Completează toate câmpurile."
                    return@Button
                }

                if (isEditMode) {
                    val bookId = selectedBook?.id
                    if (bookId != null) {
                        libraryViewModel.updateBook(
                            id = bookId,
                            title = title.trim(),
                            author = author.trim(),
                            genre = genre.trim(),
                            description = description.trim()
                        )
                    }
                } else {
                    libraryViewModel.addBook(
                        title = title.trim(),
                        author = author.trim(),
                        genre = genre.trim(),
                        description = description.trim()
                    )
                }

                onSaveSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditMode) "Salvează modificările" else "Adaugă cartea")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Renunță")
        }
    }
}