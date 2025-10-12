package com.example.app.controller

import com.example.app.repository.Book
import com.example.app.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService,
) {
    companion object {
    }

    @GetMapping("/author/{authorId}/books")
    fun getBooksByAuthor(@PathVariable authorId: Long): List<Book> {
        return bookService.getBooksByAuthor(authorId = authorId)
    }

    @PostMapping("/book")
    fun createBook(@RequestBody request: BookRequest): BookResponse {
        return bookService.createBook(request = request)
    }

    @PutMapping("/book/{bookId}")
    fun updateBook(@PathVariable bookId: Long, @RequestBody request: BookRequest): BookResponse {
        return bookService.updateBook(bookId = bookId, request = request)
    }

    data class BookRequest(
        val title: String,
        val price: Double,
        val authorId: Long,
    )

    data class BookResponse(
        val bookId: Long,
        val title: String,
        val price: Double,
        val authorId: Long,
    )
}
