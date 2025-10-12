package com.example.app.service

import com.example.app.controller.BookController.BookResponse
import com.example.app.controller.BookController.BookRequest
import com.example.app.repository.Book
import com.example.app.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun getBooksByAuthor(authorId: Long): List<Book> {
        return bookRepository.getBooksByAuthor()
    }

    fun createBook(request: BookRequest): BookResponse {
        return BookResponse(
            bookId = 1L,
            title = "dummy",
            price = 1000.0,
            authorId = 1L,
        )
    }

    fun updateBook(bookId: Long, request: BookRequest): BookResponse {
        return BookResponse(
            bookId = bookId,
            title = "dummy",
            price = 1000.0,
            authorId = 1L,
        )
    }
}
