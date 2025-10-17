package com.example.app.queryservice

import com.example.app.domain.PublishStatus
import com.example.jooq.generated.tables.references.AUTHOR
import com.example.jooq.generated.tables.references.BOOK
import com.example.jooq.generated.tables.references.BOOK_AUTHOR
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class BookQueryService(
    private val dsl: DSLContext,
) {
    fun getBooksByAuthorId(authorId: Int): List<GetBooksByAuthorId> {
        val ba = BOOK_AUTHOR.`as`("ba")
        return dsl
            .select(
                BOOK.BOOK_ID,
                BOOK.TITLE,
                BOOK.PRICE,
                BOOK.PUBLISH_STATUS,
                DSL
                    .arrayAggDistinct(
                        BOOK_AUTHOR.AUTHOR_ID,
                    ).orderBy(BOOK_AUTHOR.AUTHOR_ID)
                    .`as`("authorIds"),
            ).from(BOOK)
            .join(BOOK_AUTHOR)
            .on(BOOK.BOOK_ID.eq(BOOK_AUTHOR.BOOK_ID))
            .join(AUTHOR)
            .on(AUTHOR.AUTHOR_ID.eq(BOOK_AUTHOR.AUTHOR_ID))
            .whereExists(
                DSL
                    .selectOne()
                    .from(ba)
                    .where(ba.BOOK_ID.eq(BOOK.BOOK_ID))
                    .and(ba.AUTHOR_ID.eq(authorId)),
            ).groupBy(BOOK.BOOK_ID, BOOK.TITLE, BOOK.PRICE, BOOK.PUBLISH_STATUS)
            .orderBy(BOOK.BOOK_ID)
            .fetchInto(GetBooksByAuthorId::class.java)
    }

    data class GetBooksByAuthorId(
        val bookId: Int,
        val title: String,
        val price: BigDecimal,
        val publishStatus: PublishStatus,
        val authorIds: List<Int>,
    )
}
