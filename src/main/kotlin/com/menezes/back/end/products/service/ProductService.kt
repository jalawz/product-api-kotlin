package com.menezes.back.end.products.service

import com.menezes.back.end.products.dto.ProductDTO
import com.menezes.back.end.products.exceptions.ProductNotFoundException
import com.menezes.back.end.products.model.Product
import com.menezes.back.end.products.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun getAllProducts() = productRepository.findAll().map { product -> ProductDTO.convert(product) }

    fun getProductByCategoryId(categoryId: Long): List<ProductDTO> {
        return productRepository.getProductByCategory(categoryId)
            .map { ProductDTO.convert(it) }
    }

    fun findByProductIdentifier(productIdentifier: String): ProductDTO {
        val product =
            productRepository
                .findByProductIdentifier(productIdentifier)
                .orElseThrow { ProductNotFoundException("Product with identifier $productIdentifier not found") }
        return ProductDTO.convert(product)
    }

    fun saveProduct(dto: ProductDTO): ProductDTO {
        val product = productRepository.save(Product.convert(dto))
        return ProductDTO.convert(product)
    }

    fun deleteProduct(productId: Long) {
        val product = findProductByIdOrThrow(productId)
        productRepository.delete(product)
    }

    fun editProduct(
        id: Long,
        dto: ProductDTO,
    ): ProductDTO {
        val product = findProductByIdOrThrow(id)
        return ProductDTO.convert(
            productRepository.save(
                product.copy(
                    name = dto.name.takeIf { it.isNotBlank() } ?: product.name,
                    price = dto.price,
                ),
            ),
        )
    }

    fun getAllProductsPaged(page: Pageable): Page<ProductDTO> {
        val products = productRepository.findAll(page)
        return products.map { ProductDTO.convert(it) }
    }

    private fun findProductByIdOrThrow(productId: Long): Product =
        productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException("Product with id $productId not found") }
}
