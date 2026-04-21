package com.ecommerce.order.domain

import com.ecommerce.order.api.OrderLine
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
class Order (
    @Column(nullable = false)
    var orderLine: List<OrderLine>,

    @Column(nullable = false)
    val memberId: Long?,

    @Column
    var totalAmount: Long

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null    // id는 DB가 생성하므로 주 생성자에서 제외
}