package com.ecommerce.order.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

// name: 호출할 서비스의 Eureka/Consul 등록 이름 (서비스 ID)
@FeignClient(
    name = "product-service",
    fallback = ProductServiceClientFallback::class
)
interface ProductServiceClient {

    // 호출할 `product-service`의 API 시그니처와 동일하게 메서드 선언
    @GetMapping("/api/v1/products/{productId}")
    fun getProduct(@PathVariable productId: Long): ProductResponse

    // 상품 재고 차감을 위한 API 호출
    @PostMapping("/api/v1/products/decrease-stock")
    fun decreaseStock(@RequestBody request: DecreaseStockRequest)

    // 끝까지 가보고 없는 부분 추가해서 만들기!
}