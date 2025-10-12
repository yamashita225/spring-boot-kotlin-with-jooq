package com.example.app.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun test() {
        // Arrange, Act, Assert
        val response = mockMvc
            .perform(get("/author/1/books"))
            .andExpect(status().isOk)
            .andReturn().response

        val actual = response.getContentAsString(StandardCharsets.UTF_8)

        println("response: $response")
        println("actual: $actual")
    }
}
