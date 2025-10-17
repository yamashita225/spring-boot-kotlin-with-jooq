package com.example.app.controller

import com.example.app.domain.PublishStatus
import com.example.app.service.BookService
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class BookController(
    private val bookService: BookService,
) {
    companion object {
    }

    @GetMapping("/author/{authorId}/books")
    fun getBooksByAuthorId(
        @PathVariable authorId: Int,
    ): List<BookResponse> = bookService.getBooksByAuthorId(authorId = authorId)

    @PostMapping("/book")
    fun createBook(
        @RequestBody request: BookRequest,
    ): BookResponse = bookService.createBook(request = request)

    @PutMapping("/book/{bookId}")
    fun updateBook(
        @PathVariable bookId: Int,
        @RequestBody request: BookRequest,
    ): BookResponse = bookService.updateBook(bookId = bookId, request = request)

    data class BookRequest(
        @field:NotBlank(message = "タイトルは必須です")
        @field:Size(max = 255, message = "タイトルは255文字以下で入力してください")
        val title: String,
        @field:DecimalMin("0.0", inclusive = true, message = "価格は0以上である必要があります")
        val price: BigDecimal,
        @field:NotBlank(message = "出版状況は必須です")
        val publishStatus: PublishStatus,
        @field:Size(min = 1, message = "著者Idは1件以上必要です")
        val authorIds: List<Int>,
    )

    data class BookResponse(
        val bookId: Int,
        val title: String,
        val price: BigDecimal,
        val publishStatus: PublishStatus,
        val authorIds: List<Int>,
    )
}
