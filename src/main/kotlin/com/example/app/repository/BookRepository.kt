package com.example.app.repository

import com.example.app.domain.Book
import com.example.app.domain.PublishStatus
import com.example.jooq.generated.tables.references.BOOK
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class BookRepository(
    private val dsl: DSLContext,
) {
    fun findById(bookId: Int): Book? =
        dsl
            .select(
                BOOK.BOOK_ID,
                BOOK.TITLE,
                BOOK.PRICE,
                BOOK.PUBLISH_STATUS,
            ).from(BOOK)
            .where(BOOK.BOOK_ID.eq(bookId))
            .fetchOneInto(Book::class.java)

    fun findByAuthorId(): List<Book> =
        dsl
            .select(
                BOOK.BOOK_ID,
                BOOK.TITLE,
                BOOK.PRICE,
                BOOK.PUBLISH_STATUS,
            ).from(BOOK)
            .fetchInto(Book::class.java)

    fun create(
        title: String,
        price: BigDecimal,
        publishStatus: PublishStatus,
    ): Book =
        dsl
            .insertInto(BOOK)
            .set(BOOK.TITLE, title)
            .set(BOOK.PRICE, price)
            .set(BOOK.PUBLISH_STATUS, publishStatus.name)
            .returningResult(BOOK.BOOK_ID, BOOK.TITLE, BOOK.PRICE, BOOK.PUBLISH_STATUS)
            .fetchOneInto(Book::class.java)!!

    fun update(book: Book): Book =
        dsl
            .update(BOOK)
            .set(BOOK.TITLE, book.title)
            .set(BOOK.PRICE, book.price)
            .set(BOOK.PUBLISH_STATUS, book.publishStatus.name)
            .where(BOOK.BOOK_ID.eq(book.bookId))
            .returningResult(BOOK.BOOK_ID, BOOK.TITLE, BOOK.PRICE, BOOK.PUBLISH_STATUS)
            .fetchOneInto(Book::class.java)!!
}
