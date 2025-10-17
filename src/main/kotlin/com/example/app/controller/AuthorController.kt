package com.example.app.controller

import com.example.app.service.AuthorService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class AuthorController(
    private val authorService: AuthorService,
) {
    @PostMapping("/author")
    fun createAuthor(
        @Valid @RequestBody request: AuthorRequest,
    ): AuthorResponse = authorService.createAuthor(request = request)

    @PutMapping("/author/{authorId}")
    fun createAuthor(
        @PathVariable authorId: Int,
        @RequestBody request: AuthorRequest,
    ): AuthorResponse = authorService.updateAuthor(authorId = authorId, request = request)

    data class AuthorRequest(
        @field:NotBlank(message = "名前は必須です")
        @field:Size(max = 255, message = "名前は255文字以下で入力してください")
        val name: String,

        @field:Past(message = "生年月日は過去の日付を指定してください")
        val birthDate: LocalDate,
    )

    data class AuthorResponse(
        val authorId: Int,
        val name: String,
        val birthDate: LocalDate,
    )
}
