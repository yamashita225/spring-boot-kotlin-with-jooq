package com.example.app.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("/book")
class BookController {
    companion object {
    }

    @GetMapping("/list")
    fun getBooksByAuthor(@RequestParam id: Int = 0): Book {
        return Book(title = "テスト")
    }

}
