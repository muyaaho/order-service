package com.ecommerce.order.api

// 주문정보 응답 DTO
data class OrderResponse (
    val id: Long,
    val orderLine: OrderLine,
    val status: OrderLineStatus
)

data class OrderLine(
    val productId: Long,
    val price: Long,
    val quantity: Int,
    val status:OrderLineStatus
)

enum class OrderLineStatus{PENDING, CONFIRMED, SHIPPED}