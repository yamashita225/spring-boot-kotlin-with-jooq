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
    fun getBooksByAuthorId(authorId: Int): List<BookResponse> {
        return bookRepository.getBooksByAuthorId()
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
             title = request.title,
             price = request.price,
             publishStatus = PublishStatus.PUBLISHED
         )
        val result = bookRepository.create(book = book)

        return BookResponse(
            bookId = result.bookId,
            title = result.title,
            price = result.price,
            publishStatus = result.publishStatus,
        )
    }

    fun updateBook(bookId: Int, request: BookRequest): BookResponse {
        val book = Book(
            bookId = bookId,
            title = request.title,
            price = request.price,
            publishStatus = request.publishStatus
        )
        val result = bookRepository.update(book = book)

        return BookResponse(
            bookId = result.bookId,
            title = result.title,
            price = result.price,
            publishStatus = result.publishStatus,
        )
    }
}
