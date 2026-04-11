package com.ecommerce.order.client

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ProductServiceClientFallback : ProductServiceClient {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 이 메서드는 ProductServiceClient.getProduct() 호출이 실패하면
     * (서킷 OPEN, 타임아웃 등) 대신 호출된다.
     */
    override fun getProduct(productId: Long): ProductResponse {
        log.warn("Fallback for getProduct(productId={}) triggered.", productId)

        return ProductResponse(
            id = productId,
            name = "상품 정보 조회 불가",
            price = 1L,
            stockQuantity = 0
        )
    }
}