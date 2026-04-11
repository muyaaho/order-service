package com.ecommerce.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableFeignClients
class OrderApplication

fun main(args: Array<String>) {
    runApplication<OrderApplication>(*args)
}