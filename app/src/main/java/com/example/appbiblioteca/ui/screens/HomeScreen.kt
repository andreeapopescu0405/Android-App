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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appbiblioteca.model.UserRole
import com.example.appbiblioteca.ui.components.BookCard
import com.example.appbiblioteca.ui.components.SearchBar
import com.example.appbiblioteca.viewmodel.AuthViewModel
import com.example.appbiblioteca.viewmodel.LibraryViewModel

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    libraryViewModel: LibraryViewModel,
    onBookClick: () -> Unit,
    onAddBookClick: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    val books = libraryViewModel.books.value
    val currentUser = authViewModel.currentUser.value
    val isAdmin = currentUser?.role == UserRole.ADMIN

    val filteredBooks = books.filter {
        it.title.contains(searchText, ignoreCase = true) ||
                it.author.contains(searchText, ignoreCase = true) ||
                it.genre.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Bun venit, ${currentUser?.name ?: "Utilizator"}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        SearchBar(
            text = searchText,
            onTextChange = { searchText = it }
        )

        if (isAdmin) {
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onAddBookClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adaugă carte")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredBooks) { book ->
                BookCard(
                    book = book,
                    onClick = {
                        libraryViewModel.selectBook(book)
                        onBookClick()
                    }
                )
            }
        }
    }
}