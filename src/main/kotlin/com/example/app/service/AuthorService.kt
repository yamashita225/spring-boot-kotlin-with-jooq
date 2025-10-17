package com.example.app.service

import com.example.app.controller.AuthorController.AuthorRequest
import com.example.app.controller.AuthorController.AuthorResponse
import com.example.app.domain.Author
import com.example.app.repository.AuthorRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,
) {
    fun createAuthor(request: AuthorRequest): AuthorResponse {
        val result = authorRepository.create(name = request.name, birthDate = request.birthDate)

        return AuthorResponse(
            authorId = result.authorId,
            name = result.name,
            birthDate = result.birthDate,
        )
    }

    fun updateAuthor(
        authorId: Int,
        request: AuthorRequest,
    ): AuthorResponse {
        // 更新対象データが取得できない場合はエラー
        authorRepository.findById(authorId = authorId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Book not found with authorId=$authorId",
            )

        val updatedAuthor =
            Author.update(
                authorId = authorId,
                name = request.name,
                birthDate = request.birthDate,
            )
        val result = authorRepository.update(author = updatedAuthor)

        return AuthorResponse(
            authorId = result.authorId,
            name = result.name,
            birthDate = result.birthDate,
        )
    }
}
