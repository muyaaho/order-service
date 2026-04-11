package com.ecommerce.order.service

import com.ecommerce.order.client.ProductServiceClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productServiceClient: ProductServiceClient // 1. Feign Client 주입
) {
    @Transactional
    fun createOrder(memberId: Long, request: CreateOrderRequest) {
        // 2. 마치 로컬 메서드를 호출하듯 간결하게 API 호출
        val product = productServiceClient.getProduct(request.productId)

        // 3. 재고 확인 로직
        if (product.stockQuantity < request.quantity) {
            throw IllegalArgumentException("재고가 부족합니다.")
        }
        // ... (주문 생성 로직) ...
    }
}