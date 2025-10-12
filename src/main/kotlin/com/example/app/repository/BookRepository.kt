package com.example.app.repository

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class BookRepository(
    private val dsl: DSLContext
) {
    fun getBooksByAuthor(): List<Book> {
        return dsl
            .select(
                DSL.field("book_id", Long::class.java),
                DSL.field("title", String::class.java),
                DSL.field("price", BigDecimal::class.java),
//                DSL.field("publish_status", String::class.java),
            )
            .from("book")
            .fetch()
            .map {
                Book(
                    bookId = it.value1(),
                    title = it.value2(),
                    price = it.value3(),
//                    publishStatus = it.value4(),
                )
            }
    }
}
