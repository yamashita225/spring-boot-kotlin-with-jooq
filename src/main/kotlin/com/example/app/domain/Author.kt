package com.example.app.domain

import java.time.LocalDate

data class Author(
    val authorId: Int,
    val name: String,
    val birthDate: LocalDate,
){
    companion object {
        fun validate(name: String, birthDate: LocalDate) {
            require(name.length <= 255) { "名前は255文字以下にしてください" }
            require(birthDate < LocalDate.now()) { "誕生日は過去の日付を設定してください" }
        }

        fun update(authorId: Int, name: String, birthDate: LocalDate): Author {
            validate(name = name, birthDate = birthDate)
            return Author(
                authorId = authorId,
                name = name,
                birthDate = birthDate,
            )
        }
    }
}
