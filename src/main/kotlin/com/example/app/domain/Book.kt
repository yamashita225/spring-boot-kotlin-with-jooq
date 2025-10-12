package com.example.app.domain

data class Book(
    val bookId: Long,
    val title: String,
    val price: Double,
    val authorId: Long,
)
