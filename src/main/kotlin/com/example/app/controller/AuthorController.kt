package com.example.app.controller

import com.example.app.service.AuthorService
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
        @RequestBody request: AuthorRequest,
    ): AuthorResponse = authorService.createAuthor(request = request)

    @PutMapping("/author/{authorId}")
    fun createAuthor(
        @PathVariable authorId: Int,
        @RequestBody request: AuthorRequest,
    ): AuthorResponse = authorService.updateAuthor(authorId = authorId, request = request)

    data class AuthorRequest(
        val name: String,
        val birthDate: LocalDate,
    )

    data class AuthorResponse(
        val authorId: Int,
        val name: String,
        val birthDate: LocalDate,
    )
}
