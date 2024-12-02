package com.menezes.back.end.products.repository

import com.menezes.back.end.products.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>
