package com.example.app.repository

import com.example.app.domain.Book
import com.example.jooq.generated.tables.references.BOOK
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookRepository(
    private val dsl: DSLContext
) {
    fun getBooksByAuthorId(): List<Book> {
        return dsl.select(
                BOOK.BOOK_ID,
                BOOK.TITLE,
                BOOK.PRICE,
                BOOK.PUBLISH_STATUS
            )
            .from(BOOK)
            .fetchInto(Book::class.java)
    }

    fun create(book: Book): Book {
        return dsl.insertInto(BOOK)
            .set(BOOK.BOOK_ID, book.bookId)
            .set(BOOK.TITLE, book.title)
            .set(BOOK.PRICE, book.price)
            .set(BOOK.PUBLISH_STATUS, book.publishStatus.name)
            .returningResult(BOOK.BOOK_ID, BOOK.TITLE, BOOK.PRICE, BOOK.PUBLISH_STATUS)
            .fetchOneInto(Book::class.java)!!
    }

    fun update(book: Book): Book {
        return dsl.update(BOOK)
            .set(BOOK.TITLE, book.title)
            .set(BOOK.PRICE, book.price)
            .set(BOOK.PUBLISH_STATUS, book.publishStatus.name)
            .where(BOOK.BOOK_ID.eq(book.bookId))
            .returningResult(BOOK.BOOK_ID, BOOK.TITLE, BOOK.PRICE, BOOK.PUBLISH_STATUS)
            .fetchOneInto(Book::class.java)!!
    }
}
