package com.example.app.domain

import java.math.BigDecimal

data class Book(
    val bookId: Int,
    val title: String,
    val price: BigDecimal,
    val publishStatus: PublishStatus,
){
    companion object {
        fun validate(
            title: String,
            price: BigDecimal,
        ) {
            require(title.length <= 255) { "titleは255文字以下にしてください" }
            require(price >= BigDecimal.ZERO) { "priceは0以上にしてください" }
        }

        fun update(book: Book, oldPublishStatus: PublishStatus): Book{
            validate(title = book.title, price = book.price)
            if(oldPublishStatus == PublishStatus.PUBLISHED){
                require(book.publishStatus == PublishStatus.PUBLISHED) { "publishStatusは出版済みから未出版に変更できません" }
            }
            return book
        }
    }
}

enum class PublishStatus {
    UNPUBLISHED,
    PUBLISHED;

    companion object {
        fun from(value: String): PublishStatus {
            return entries.firstOrNull { it.name == value }
                ?: throw IllegalArgumentException("Invalid PublishStatus: $value")
        }
    }
}


