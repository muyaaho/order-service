package com.ecommerce.order.client

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val stockQuantity: Int,
)