package com.menezes.back.end.products.dto

import com.menezes.back.end.products.model.Product
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductDTO(
    @NotBlank
    val productIdentifier: String,
    @NotBlank
    val name: String,
    @NotBlank
    val description: String,
    @NotNull
    val price: Float,
    @NotNull
    val category: CategoryDTO,
) {
    companion object {
        fun convert(product: Product) =
            ProductDTO(
                name = product.name,
                price = product.price,
                productIdentifier = product.productIdentifier,
                description = product.description,
                category =
                    product.category.let {
                        CategoryDTO.convert(it)
                    },
            )
    }
}
