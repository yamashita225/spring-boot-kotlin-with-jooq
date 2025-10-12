package com.example.app.controller

import com.example.app.service.AuthorService
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorController(authorService: AuthorService) {
}
