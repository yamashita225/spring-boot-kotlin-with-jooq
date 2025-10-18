package com.example.app.config

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
    ): ResponseEntity<Map<String, Any>> {
        val errors =
            ex.bindingResult.fieldErrors.map {
                mapOf("field" to it.field, "message" to (it.defaultMessage ?: "不正な値です"))
            }
        return ResponseEntity.badRequest().body(mapOf("errors" to errors))
    }

    // enumのバリデーションエラー時のhandler
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException::class)
    fun handleInvalidFormatException(
        ex: com.fasterxml.jackson.databind.exc.InvalidFormatException,
    ): ResponseEntity<Map<String, Any>> {
        val errors = listOf(mapOf("message" to "指定された値が不正です: ${ex.value}"))
        return ResponseEntity.badRequest().body(
            mapOf("errors" to errors),
        )
    }
}
