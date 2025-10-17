package com.example.app.repository

import com.example.app.domain.BookAuthor
import com.example.jooq.generated.tables.references.BOOK_AUTHOR
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookAuthorRepository(
    private val dsl: DSLContext,
) {
    fun create(
        bookId: Int,
        authorIds: List<Int>,
    ): List<BookAuthor> {
        val result =
            dsl
                .insertInto(BOOK_AUTHOR, BOOK_AUTHOR.BOOK_ID, BOOK_AUTHOR.AUTHOR_ID)
                .apply {
                    authorIds.forEach { authorId ->
                        values(bookId, authorId)
                    }
                }.returningResult(BOOK_AUTHOR.BOOK_ID, BOOK_AUTHOR.AUTHOR_ID)
                .fetch()

        return result.map { record ->
            BookAuthor(
                bookId = record[BOOK_AUTHOR.BOOK_ID]!!,
                authorId = record[BOOK_AUTHOR.AUTHOR_ID]!!,
            )
        }
    }

    fun delete(bookId: Int): Int =
        dsl
            .delete(BOOK_AUTHOR)
            .where(BOOK_AUTHOR.BOOK_ID.eq(bookId))
            .execute()
}
