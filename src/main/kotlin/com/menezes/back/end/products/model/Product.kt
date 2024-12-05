package com.menezes.back.end.products.model

import com.menezes.backend.client.dto.ProductDTO
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val price: Float,
    val description: String,
    val productIdentifier: String,
    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: Category,
) {
    companion object {
        fun convert(dto: ProductDTO) =
            Product(
                name = dto.name,
                price = dto.price,
                description = dto.description,
                productIdentifier = dto.productIdentifier,
                category =
                    dto.category.let {
                        Category.convert(it)
                    },
            )
    }
}
