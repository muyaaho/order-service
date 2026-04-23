package com.ecommerce.order.service

import com.ecommerce.order.api.CreateOrderRequest
import com.ecommerce.order.api.OrderResponse
import com.ecommerce.order.api.markAsPaid
import com.ecommerce.order.api.toEntity
import com.ecommerce.order.api.toResponse
import com.ecommerce.order.client.ProductServiceClient
import com.ecommerce.order.domain.OrderRepository
import com.ecommerce.order.event.OrderPaidEvent
import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productServiceClient: ProductServiceClient, // 1. Feign Client 주입
    private val streamBridge: StreamBridge, // StreamBridge 주입
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun createOrder(memberId: Long, request: CreateOrderRequest): OrderResponse {
        // 2. 마치 로컬 메서드를 호출하듯 간결하게 API 호출
        val product = productServiceClient.getProduct(request.productId)

        // 3. 재고 확인 로직
        if (product.stockQuantity < request.quantity) {
            throw IllegalArgumentException("재고가 부족합니다.")
        }
        // request DTO -> Order Entity 변환
        val newOrder = request.toEntity(product.price)
        val savedOrder = orderRepository.save(newOrder)
        // 결제 서비스 연동 로직
        // paymentService.processPayment(...)
        // 결제가 성공했다고 가정
        savedOrder.markAsPaid()

        // 이벤트 생성
        val event = OrderPaidEvent(
            orderId = savedOrder.id!!,
            memberId = savedOrder.memberId,
            totalAmount = savedOrder.totalAmount.toBigDecimal(),
            orderLines = savedOrder.orderLines.map {
                OrderPaidEvent.OrderLineItem(it.productId, it.quantity)
            }
        )

        // StreamBridge로 이벤트 발행
        // 첫 번째 인자: application.yml에 정의한 바인딩 이름
        // 두 번째 인자: 보낼 이벤트 객체
        val isSent = streamBridge.send("orderPaidEventProducer-out-0", event)
        log.info("OrderPaidEvent sent for orderId {}: {}", savedOrder.id, isSent)

        return savedOrder.toResponse()
    }

    fun getOrder(orderId: Long): OrderResponse {
        val order = orderRepository.findByOrderId(orderId)
            ?: throw EntityNotFoundException("해당 ID의 주문이 없습니다: $orderId")

        return order.toResponse()
    }
}