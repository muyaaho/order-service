package com.ecommerce.order.client

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val stockQuantity: Int,
)

data class CreateOrderRequest (
    val productId: Long,           // 어떤걸 요청했는지
    val quantity: Int,      // 몇 개 요청했는지
)

