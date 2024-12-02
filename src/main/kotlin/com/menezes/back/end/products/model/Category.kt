package com.menezes.back.end.products.model

import com.menezes.back.end.products.dto.CategoryDTO
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "category")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String?,
) {
    companion object {
        fun convert(dto: CategoryDTO) =
            Category(
                id = dto.id,
                name = dto.name
            )
    }
}
