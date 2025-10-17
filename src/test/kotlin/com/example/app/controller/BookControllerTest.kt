package com.example.app.controller

import com.example.app.TestContainersConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.function.RequestPredicates.contentType

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfiguration::class)
class BookControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `GET - should return books by author`() {
        mockMvc
            .get("/author/1/books")
            .andExpect {
                status { isOk() }
                jsonPath("$.size()") { value(2) }
                jsonPath("$[0].title") { value("吾輩は猫である") }
                jsonPath("$[1].title") { value("こころ") }
            }
    }

    @Test
    fun `POST - should create a new book`() {
        val requestJson =
            """
            {
                "title": "1Q84",
                "price": 1500.00,
                "publishStatus": "PUBLISHED",
                "authorIds": [2]
            }
            """.trimIndent()

        mockMvc
            .post("/book") {
                contentType = MediaType.APPLICATION_JSON
                content = requestJson
            }.andExpect {
                status { isOk() }
                jsonPath("$.title") { value("1Q84") }
                jsonPath("$.publishStatus") { value("PUBLISHED") }
            }
    }

    @Test
    fun `PUT - should update existing book`() {
        val requestJson =
            """
            {
                "title": "容疑者Xの献身（改訂版）",
                "price": 800.00,
                "publishStatus": "PUBLISHED",
                "authorIds": [3]
            }
            """.trimIndent()

        mockMvc
            .put("/book/3") {
                contentType = MediaType.APPLICATION_JSON
                content = requestJson
            }.andExpect {
                status { isOk() }
                jsonPath("$.title") { value("容疑者Xの献身（改訂版）") }
                jsonPath("$.publishStatus") { value("PUBLISHED") }
            }
    }
}
