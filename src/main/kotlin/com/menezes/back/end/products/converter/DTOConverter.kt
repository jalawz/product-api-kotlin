package com.menezes.back.end.products.converter

import com.menezes.back.end.products.model.Category
import com.menezes.back.end.products.model.Product
import com.menezes.backend.client.dto.CategoryDTO
import com.menezes.backend.client.dto.ProductDTO

object DTOConverter {

    private fun convert(category: Category) = CategoryDTO(
        id = category.id,
        name = category.name
    )

    fun convert(product: Product): ProductDTO {
        requireNotNull(product.category)
        return ProductDTO(
            name = product.name,
            price = product.price,
            description = product.description,
            productIdentifier = product.productIdentifier,
            category = convert(product.category)
        )
    }
}