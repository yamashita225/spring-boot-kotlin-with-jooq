package com.example.app.repository

import com.example.app.domain.Author
import com.example.jooq.generated.tables.references.AUTHOR
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class AuthorRepository(
    private val dsl: DSLContext,
) {
    fun findById(authorId: Int): Author? =
        dsl
            .select(AUTHOR.AUTHOR_ID, AUTHOR.NAME, AUTHOR.BIRTH_DATE)
            .from(AUTHOR)
            .where(AUTHOR.AUTHOR_ID.eq(authorId))
            .fetchOneInto(Author::class.java)

    fun create(
        name: String,
        birthDate: LocalDate,
    ): Author =
        dsl
            .insertInto(AUTHOR)
            .set(AUTHOR.NAME, name)
            .set(AUTHOR.BIRTH_DATE, birthDate)
            .returningResult(AUTHOR.AUTHOR_ID, AUTHOR.NAME, AUTHOR.BIRTH_DATE)
            .fetchOneInto(Author::class.java)!!

    fun update(author: Author): Author =
        dsl
            .update(AUTHOR)
            .set(AUTHOR.NAME, author.name)
            .set(AUTHOR.BIRTH_DATE, author.birthDate)
            .where(AUTHOR.AUTHOR_ID.eq(author.authorId))
            .returningResult(AUTHOR.AUTHOR_ID, AUTHOR.NAME, AUTHOR.BIRTH_DATE)
            .fetchOneInto(Author::class.java)!!
}
