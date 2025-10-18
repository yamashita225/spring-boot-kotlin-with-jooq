package com.example.app.domain

import java.math.BigDecimal

data class Book(
    val bookId: Int,
    val title: String,
    val price: BigDecimal,
    val publishStatus: PublishStatus,
) {
    companion object {
        fun update(
            book: Book,
            oldPublishStatus: PublishStatus,
        ): Book {
            if (oldPublishStatus == PublishStatus.PUBLISHED) {
                require(
                    book.publishStatus == PublishStatus.PUBLISHED,
                ) { "publishStatusは出版済みから未出版に変更できません" }
            }
            return book
        }
    }
}

enum class PublishStatus {
    UNPUBLISHED,
    PUBLISHED,
    ;

    companion object {
        fun from(value: String): PublishStatus =
            entries.firstOrNull { it.name == value }
                ?: throw IllegalArgumentException("Invalid PublishStatus: $value")
    }
}
