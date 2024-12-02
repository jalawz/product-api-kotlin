package com.menezes.back.end.products.dto

import com.menezes.back.end.products.model.Category
import jakarta.validation.constraints.NotNull

data class CategoryDTO(
    @NotNull
    val id: Long,
    val name: String?,
) {
    companion object {
        fun convert(category: Category) =
            CategoryDTO(
                id = category.id,
                name = category.name,
            )
    }
}
