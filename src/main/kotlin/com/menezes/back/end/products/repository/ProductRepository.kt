package com.menezes.back.end.products.repository

import com.menezes.back.end.products.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface ProductRepository : JpaRepository<Product, Long> {
    @Query(
        value =
            "select p " +
                "from product p " +
                "join category c on p.category.id = c.id " +
                "where c.id = :categoryId",
    )
    fun getProductByCategory(
        @Param("categoryId") categoryId: Long,
    ): List<Product>

    fun findByProductIdentifier(productIdentifier: String): Optional<Product>
}
