package com.ecommerce.order.service

import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepo
) {
}