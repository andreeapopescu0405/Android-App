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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appbiblioteca.model.Book
import com.example.appbiblioteca.ui.components.BookCard
import com.example.appbiblioteca.ui.components.SearchBar

@Composable
fun HomeScreen(onBookClick: () -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val books = listOf(
        Book(1, "1984", "George Orwell", "Distopie", "O carte despre control și libertate.", true),
        Book(2, "Ion", "Liviu Rebreanu", "Roman", "Un roman clasic românesc.", false),
        Book(3, "Micul Prinț", "Antoine de Saint-Exupéry", "Ficțiune", "O poveste simbolică.", true)
    )

    val filteredBooks = books.filter {
        it.title.contains(searchText, ignoreCase = true) ||
                it.author.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            text = searchText,
            onTextChange = { searchText = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredBooks) { book ->
                BookCard(book = book, onClick = onBookClick)
            }
        }
    }
}