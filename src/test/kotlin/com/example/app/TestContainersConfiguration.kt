package com.example.app

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.util.function.Supplier

@TestConfiguration(proxyBeanMethods = false)
class TestContainersConfiguration {
    @Bean
    @ServiceConnection
    fun postgres(): PostgreSQLContainer<*> {
        return PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:17.6-alpine"))
    }

    @DynamicPropertySource
    fun properties(registry: DynamicPropertyRegistry) {
        registry.add("spring.flyway.enabled", Supplier { "true" })
    }
}
