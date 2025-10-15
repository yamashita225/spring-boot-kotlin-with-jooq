package com.example.app.repository

import com.example.app.domain.Book
import com.example.app.domain.PublishStatus
import com.example.jooq.generated.tables.references.BOOK
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookRepository(
    private val dsl: DSLContext
) {
    fun getBooksByAuthor(): List<Book> {
        val record =  dsl
            .select(
                BOOK.BOOK_ID,
                BOOK.TITLE,
                BOOK.PRICE,
                BOOK.PUBLISH_STATUS
            )
            .from(BOOK)
            .fetch()

            return record.into(BOOK)
            .map {
                Book(
                    bookId = it.bookId!!,
                    title = it.title!!,
                    price = it.price!!,
                    publishStatus = PublishStatus.from(it.publishStatus!!),
                )
            }
    }

    fun create(book: Book): Int {
        return dsl.insertInto(BOOK)
            .set(BOOK.BOOK_ID, book.bookId)
            .set(BOOK.TITLE, book.title)
            .set(BOOK.PRICE, book.price)
            .set(BOOK.PUBLISH_STATUS, book.publishStatus.name)
            .execute()
    }

    fun update(book: Book): Int {
        return dsl.update(BOOK)
            .set(BOOK.TITLE, book.title)
            .set(BOOK.PRICE, book.price)
            .set(BOOK.PUBLISH_STATUS, book.publishStatus.name)
            .where(BOOK.BOOK_ID.eq(book.bookId))
            .execute()
    }
}
