package com.ecommerce.order.api

import com.ecommerce.order.client.ProductResponse
import com.ecommerce.order.service.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/orders")
class OrderController (
    private val orderService: OrderService  // 생성자 주입
){
    @PostMapping("/order")
    fun order(@RequestBody request: OrderLine) : OrderResponse {

    }
}