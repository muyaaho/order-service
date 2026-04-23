package com.ecommerce.order.client

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val stockQuantity: Int,
)

data class DecreaseStockRequest(
    val id: Long,
    val name: String,
    val stockQuantity: Int,
)