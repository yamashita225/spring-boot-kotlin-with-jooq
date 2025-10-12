package com.example.app.repository

import java.math.BigDecimal

data class Book(
    val bookId: Long,
    val title: String,
    val price: BigDecimal,
//    val publishStatus: PublishStatus,
)

enum class PublishStatus {
    UNPUBLISHED,
    PUBLISHED
}
