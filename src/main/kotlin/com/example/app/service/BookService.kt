package com.example.app.service

import com.example.app.controller.BookController.BookRequest
import com.example.app.controller.BookController.BookResponse
import com.example.app.domain.Book
import com.example.app.domain.PublishStatus
import com.example.app.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun getBooksByAuthorId(authorId: Int): List<BookResponse> =
        bookRepository
            .getBooksByAuthorId()
            .map {
                BookResponse(
                    bookId = it.bookId,
                    title = it.title,
                    price = it.price,
                    publishStatus = it.publishStatus,
                )
            }

    fun createBook(request: BookRequest): BookResponse {
        Book.validate(title = request.title, price = request.price)

        val result =
            bookRepository.create(
                title = request.title,
                price = request.price,
                publishStatus = PublishStatus.PUBLISHED, // 登録時はPUBLISHED固定
            )

        // TODO: authorBookテーブルに登録

        return BookResponse(
            bookId = result.bookId,
            title = result.title,
            price = result.price,
            publishStatus = result.publishStatus,
        )
    }

    fun updateBook(
        bookId: Int,
        request: BookRequest,
    ): BookResponse {
        // TODO: 更新対象データを取得、取得できない場合はエラー

        val updatedBook =
            Book.update(
                book =
                    Book(
                        bookId = bookId,
                        title = request.title,
                        price = request.price,
                        publishStatus = request.publishStatus,
                    ),
                oldPublishStatus = PublishStatus.PUBLISHED,
            )
        val result = bookRepository.update(book = updatedBook)

        // TODO: authorBookテーブルに登録

        return BookResponse(
            bookId = result.bookId,
            title = result.title,
            price = result.price,
            publishStatus = result.publishStatus,
        )
    }
}
