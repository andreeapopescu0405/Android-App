package com.example.appbiblioteca.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String,
    val description: String,
    val available: Boolean = true,
    val rentedByUserId: Int? = null
)