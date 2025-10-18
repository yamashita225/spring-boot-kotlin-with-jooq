package com.example.app.domain

import java.time.LocalDate

data class Author(
    val authorId: Int,
    val name: String,
    val birthDate: LocalDate,
) {
    companion object {
        // 引数はauthorにした方がいい？
        fun update(
            authorId: Int,
            name: String,
            birthDate: LocalDate,
        ): Author =
            Author(
                authorId = authorId,
                name = name,
                birthDate = birthDate,
            )
    }
}
