package com.example.app.controller

import com.example.app.TestContainersConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfiguration::class)
class AuthorControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `POST - should create a new author`() {
        val requestJson =
            """
            {
                "name": "芥川龍之介",
                "birthDate": "1892-03-01"
            }
            """.trimIndent()

        mockMvc
            .post("/author") {
                contentType = MediaType.APPLICATION_JSON
                content = requestJson
            }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.authorId") { exists() }
                jsonPath("$.name") { value("芥川龍之介") }
                jsonPath("$.birthDate") { value("1892-03-01") }
            }
    }

    @Test
    fun `PUT - should update existing author`() {
        // 事前データ（Flywayで挿入された author_id=1 のデータを想定）
        val requestJson =
            """
            {
                "name": "夏目 漱石（改訂）",
                "birthDate": "1867-02-09"
            }
            """.trimIndent()

            mockMvc
                .put("/author/1") {
                    contentType = MediaType.APPLICATION_JSON
                    content = requestJson
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.authorId") { value(1) }
                    jsonPath("$.name") { value("夏目 漱石（改訂）") }
                }
    }

    @Test
    fun `should return 400 when name is blank`() {
        val requestJson =
            """
            {
                "name": "",
                "birthDate": "1892-03-01"
            }
            """.trimIndent()

        mockMvc
            .post("/author") {
                contentType = MediaType.APPLICATION_JSON
                content = requestJson
            }.andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun `should return 400 when birthDate is in the future`() {
        val requestJson =
            """
            {
                "name": "山田太郎",
                "birthDate": "2100-03-01"
            }
            """.trimIndent()

        mockMvc
            .post("/author") {
                contentType = MediaType.APPLICATION_JSON
                content = requestJson
            }.andExpect {
                status { isBadRequest() }
            }
    }
}
