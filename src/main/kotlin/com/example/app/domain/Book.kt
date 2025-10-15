package com.example.app.domain

import java.math.BigDecimal

data class Book(
    val bookId: Int,
    val title: String,
    val price: BigDecimal,
    val publishStatus: PublishStatus,
)

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
