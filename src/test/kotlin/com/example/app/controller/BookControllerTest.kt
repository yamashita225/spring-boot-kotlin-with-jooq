package com.example.app.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun test() {
        // Arrange, Act, Assert
        mockMvc
            .perform(MockMvcRequestBuilders.get("/book/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}
