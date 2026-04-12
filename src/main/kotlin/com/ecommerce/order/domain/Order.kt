package com.ecommerce.order.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,    // id는 DB가 생성하므로 주 생성자에서 제외

    @Column(nullable = false)
    val productId: Long,    // 제품 Id

    @Column(nullable = false)
    val quantity: Int,      // 수량

    @Column(nullable = false)
    val memberId: Long,     // 누가 시켰는지

){
}