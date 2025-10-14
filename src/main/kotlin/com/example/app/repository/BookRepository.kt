package com.example.app.repository

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
}
