package com.ecommerce.order.api

import com.ecommerce.order.domain.Order


// 주문정보 응답 DTO
data class OrderResponse (
    val id: Long,
    val memberId: Long,
    val orderLines: MutableList<OrderLine>,
)

data class OrderLine(
    val productId: Long,
    val price: Long,
    var quantity: Int,
    var status:OrderLineStatus
)

data class CreateOrderRequest (
    val productId: Long,           // 어떤걸 요청했는지
    val quantity: Int,      // 몇 개 요청했는지
    val memberId: Long,     // 누가 요청했는지
)

enum class OrderLineStatus{PENDING, CONFIRMED, SHIPPED, CANCELLED}

// Entity -> DTO 변환
fun Order.toResponse(): OrderResponse {
    return OrderResponse (
        id = this.id!!,
        memberId = this.memberId,
        orderLines = this.orderLines,
    )
}

fun CreateOrderRequest.toEntity(price: Long): Order {
    val order = Order(
        memberId = this.memberId,
        status = OrderLineStatus.PENDING    // 처음 주문 시 기본 상태
    )

    order.addOrderLine(
        productId = this.productId,
        price = price,
        quantity = this.quantity,
    )

    return order
}

fun Order.markAsPaid() {
    status = OrderLineStatus.CONFIRMED
}
