package com.ecommerce.order.service

import com.ecommerce.order.api.CreateOrderRequest
import com.ecommerce.order.api.OrderResponse
import com.ecommerce.order.api.toEntity
import com.ecommerce.order.api.toResponse
import com.ecommerce.order.client.ProductServiceClient
import com.ecommerce.order.domain.OrderRepository
import jakarta.persistence.EntityNotFoundException
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
        // request DTO -> Order Entity 변환
        val newOrder = request.toEntity(product.price)
        // [TODO] DB에 저장
        // TODO Entity -> DTO 변환 후 반환

    }

    fun getOrder(orderId: Long): OrderResponse {
        val order = orderRepository.findByOrderId(orderId)
            ?: throw EntityNotFoundException("해당 ID의 주문이 없습니다: $orderId")

        return order.toResponse()
    }
}