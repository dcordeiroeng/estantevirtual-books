package com.estantevirtual.books.utils

import com.estantevirtual.books.BooksApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Logger {
    @Bean
    fun getLogger(): Logger {
        return LoggerFactory.getLogger(BooksApplication::class.java)
    }
}