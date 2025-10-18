package com.example.app.service

import com.example.app.controller.BookController.BookRequest
import com.example.app.controller.BookController.BookResponse
import com.example.app.domain.Book
import com.example.app.domain.PublishStatus
import com.example.app.queryservice.BookQueryService
import com.example.app.repository.AuthorRepository
import com.example.app.repository.BookAuthorRepository
import com.example.app.repository.BookRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class BookService(
    private val authorRepository: AuthorRepository,
    private val bookAuthorRepository: BookAuthorRepository,
    private val bookRepository: BookRepository,
    private val bookQueryService: BookQueryService,
) {
    fun getBooksByAuthorId(authorId: Int): List<BookResponse> =
        bookQueryService
            .getBooksByAuthorId(authorId = authorId)
            .map {
                BookResponse(
                    bookId = it.bookId,
                    title = it.title,
                    price = it.price,
                    publishStatus = it.publishStatus,
                    authorIds = it.authorIds,
                )
            }

    @Transactional
    fun createBook(request: BookRequest): BookResponse {
        isAuthorRegistered(authorIds = request.authorIds)

        val bookResult =
            bookRepository.create(
                title = request.title,
                price = request.price,
                publishStatus = PublishStatus.PUBLISHED, // 登録時はPUBLISHED固定
            )

        // authorBookテーブルに登録
        val authorIds =
            bookAuthorRepository
                .create(
                    bookId = bookResult.bookId,
                    authorIds = request.authorIds,
                ).map {
                    it.authorId
                }

        return BookResponse(
            bookId = bookResult.bookId,
            title = bookResult.title,
            price = bookResult.price,
            publishStatus = bookResult.publishStatus,
            authorIds = authorIds,
        )
    }

    @Transactional
    fun updateBook(
        bookId: Int,
        request: BookRequest,
    ): BookResponse {
        // 更新対象データが取得できない場合はエラー
        bookRepository.findById(bookId = bookId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Book not found with bookId=$bookId",
            )

        isAuthorRegistered(authorIds = request.authorIds)

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
        val bookResult = bookRepository.update(book = updatedBook)

        // authorBookテーブルを削除して登録
        bookAuthorRepository.delete(bookId = bookId)
        val authorIds =
            bookAuthorRepository
                .create(
                    bookId = bookResult.bookId,
                    authorIds = request.authorIds,
                ).map {
                    it.authorId
                }

        return BookResponse(
            bookId = bookResult.bookId,
            title = bookResult.title,
            price = bookResult.price,
            publishStatus = bookResult.publishStatus,
            authorIds = authorIds,
        )
    }

    private fun isAuthorRegistered(authorIds: List<Int>) {
        val registeredAuthorId =
            authorRepository.findByIds(authorIds = authorIds).associateBy {
                it.authorId
            }

        authorIds.forEach {
            registeredAuthorId[it]
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Author not found with authorId=$it",
                )
        }
    }
}
