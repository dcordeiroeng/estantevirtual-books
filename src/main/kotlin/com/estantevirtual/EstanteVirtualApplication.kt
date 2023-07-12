package com.estantevirtual

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class EstanteVirtualApplication

fun main(args: Array<String>) {
    SpringApplication.run(EstanteVirtualApplication::class.java, *args)
}
