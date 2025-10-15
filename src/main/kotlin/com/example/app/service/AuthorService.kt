package com.example.app.service

import com.example.app.controller.AuthorController.AuthorResponse
import com.example.app.controller.AuthorController.AuthorRequest
import com.example.app.domain.Author
import com.example.app.repository.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorService(private val authorRepository: AuthorRepository) {
    fun createAuthor(request: AuthorRequest): AuthorResponse {
        Author.validate(
            name = request.name,
            birthDate = request.birthDate
        )
        val result = authorRepository.create(name = request.name, birthDate = request.birthDate)

        return AuthorResponse(
            authorId = result.authorId,
            name = result.name,
            birthDate = result.birthDate,
        )
    }

    fun updateAuthor(authorId: Int, request: AuthorRequest): AuthorResponse {
        val author = Author.update(
            authorId = authorId,
            name = request.name,
            birthDate = request.birthDate
        )
        val result = authorRepository.update(author = author)

        return AuthorResponse(
            authorId = result.authorId,
            name = result.name,
            birthDate = result.birthDate,
        )
    }
}
