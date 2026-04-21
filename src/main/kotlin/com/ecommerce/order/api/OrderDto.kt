package com.ecommerce.order.api

// 주문정보 응답 DTO
data class OrderResponse (
    val id: Long,
    val orderLine: OrderLine,
    val memberId: Long,
    val totalAmount: Long,
)

data class OrderLine(
    val productId: Long,
    val price: Long,
    var quantity: Int,
    var status:OrderLineStatus
)

enum class OrderLineStatus{PENDING, CONFIRMED, SHIPPED}