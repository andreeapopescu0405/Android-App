package com.example.appbiblioteca.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.appbiblioteca.model.Book

class LibraryViewModel : ViewModel() {

    private val _books = mutableStateOf(
        listOf(
            Book(
                id = 1,
                title = "1984",
                author = "George Orwell",
                genre = "Distopie",
                description = "O carte despre control, supraveghere și libertate.",
                available = true,
                rentedByUserId = null
            ),
            Book(
                id = 2,
                title = "Ion",
                author = "Liviu Rebreanu",
                genre = "Roman",
                description = "Un roman clasic al literaturii române.",
                available = false,
                rentedByUserId = 999
            ),
            Book(
                id = 3,
                title = "Micul Prinț",
                author = "Antoine de Saint-Exupéry",
                genre = "Ficțiune",
                description = "O poveste simbolică despre oameni, copilărie și sens.",
                available = true,
                rentedByUserId = null
            )
        )
    )
    val books: State<List<Book>> = _books

    private val _selectedBook = mutableStateOf<Book?>(null)
    val selectedBook: State<Book?> = _selectedBook

    fun selectBook(book: Book) {
        _selectedBook.value = book
    }

    fun getBookById(id: Int): Book? {
        return _books.value.find { it.id == id }
    }

    fun addBook(
        title: String,
        author: String,
        genre: String,
        description: String
    ) {
        val newId = (_books.value.maxOfOrNull { it.id } ?: 0) + 1

        val newBook = Book(
            id = newId,
            title = title,
            author = author,
            genre = genre,
            description = description,
            available = true,
            rentedByUserId = null
        )

        _books.value = _books.value + newBook
    }

    fun updateBook(
        id: Int,
        title: String,
        author: String,
        genre: String,
        description: String
    ) {
        _books.value = _books.value.map { book ->
            if (book.id == id) {
                book.copy(
                    title = title,
                    author = author,
                    genre = genre,
                    description = description
                )
            } else {
                book
            }
        }

        _selectedBook.value = _books.value.find { it.id == id }
    }

    fun deleteBook(id: Int) {
        _books.value = _books.value.filterNot { it.id == id }

        if (_selectedBook.value?.id == id) {
            _selectedBook.value = null
        }
    }

    fun rentBook(bookId: Int, userId: Int) {
        _books.value = _books.value.map { book ->
            if (book.id == bookId && book.available) {
                book.copy(
                    available = false,
                    rentedByUserId = userId
                )
            } else {
                book
            }
        }

        _selectedBook.value = _books.value.find { it.id == bookId }
    }

    fun returnBook(bookId: Int, userId: Int) {
        _books.value = _books.value.map { book ->
            if (book.id == bookId && book.rentedByUserId == userId) {
                book.copy(
                    available = true,
                    rentedByUserId = null
                )
            } else {
                book
            }
        }

        _selectedBook.value = _books.value.find { it.id == bookId }
    }

    fun getRentedBooksForUser(userId: Int): List<Book> {
        return _books.value.filter { it.rentedByUserId == userId }
    }
}