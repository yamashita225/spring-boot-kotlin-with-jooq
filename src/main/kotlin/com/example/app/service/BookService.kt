package com.example.app.service

import com.example.app.controller.BookController.BookResponse
import com.example.app.controller.BookController.BookRequest
import com.example.app.domain.Book
import com.example.app.domain.PublishStatus
import com.example.app.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun getBooksByAuthor(authorId: Int): List<BookResponse> {
        return bookRepository.getBooksByAuthor()
            .map{ BookResponse(
                bookId = it.bookId,
                title = it.title,
                price = it.price,
                publishStatus = it.publishStatus,
            )
            }
    }

    fun createBook(request: BookRequest): BookResponse {
         val book = Book(
             bookId = 1,
             title = "dummy",
             price = 100.toBigDecimal(),
             publishStatus = PublishStatus.PUBLISHED
         )
        bookRepository.create(book = book)

        return BookResponse(
            bookId = book.bookId,
            title = book.title,
            price = book.price,
            publishStatus = book.publishStatus,
        )
    }

    fun updateBook(bookId: Int, request: BookRequest): BookResponse {
        val book = Book(
            bookId = 1,
            title = "dummy",
            price = 100.toBigDecimal(),
            publishStatus = PublishStatus.PUBLISHED
        )
        bookRepository.update(book = book)

        return BookResponse(
            bookId = book.bookId,
            title = book.title,
            price = book.price,
            publishStatus = book.publishStatus,
        )
    }
}
