package com.ecommerce.order.domain

import com.ecommerce.order.api.OrderLine
import com.ecommerce.order.api.OrderLineStatus
import jakarta.persistence.CascadeType

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.lang.IllegalStateException

@Entity
@Table(name = "orders")
class Order (

    // 2장 05 참고
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderLines: MutableList<OrderLine> = mutableListOf(),
    var totalAmount: Long = 0,
    var status: OrderLineStatus,
    val memberId: Long,

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null    // id는 DB가 생성하므로 주 생성자에서 제외

    fun addOrderLine(productId: Long, price:Long, quantity: Int) {
        // 규칙1 검사: 배송 중이면 추가 불가
        if (this.status == OrderLineStatus.SHIPPED || this.status == OrderLineStatus.CANCELLED) {
            throw IllegalStateException("이미 배송되었거나 취소된 주문입니다.")
        }

        // 내부 객체 추가
        val newOrderLine = OrderLine(productId, price, quantity, OrderLineStatus.PENDING)
        this.orderLines.add(newOrderLine)

        recalculateTotalAmount()
    }

    private fun recalculateTotalAmount() {
        this.totalAmount = orderLines.sumOf{it.price * it.quantity}
    }
}