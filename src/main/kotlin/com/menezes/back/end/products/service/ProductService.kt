package com.menezes.back.end.products.service

import com.menezes.back.end.products.converter.DTOConverter
import com.menezes.back.end.products.exceptions.ProductNotFoundException
import com.menezes.back.end.products.model.Product
import com.menezes.back.end.products.repository.ProductRepository
import com.menezes.backend.client.dto.ProductDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun getAllProducts() = productRepository.findAll().map { product -> DTOConverter.convert(product) }

    fun getProductByCategoryId(categoryId: Long): List<ProductDTO> {
        return productRepository.getProductByCategory(categoryId)
            .map { DTOConverter.convert(it) }
    }

    fun findByProductIdentifier(productIdentifier: String): ProductDTO {
        val product =
            productRepository
                .findByProductIdentifier(productIdentifier)
                .orElseThrow { ProductNotFoundException("Product with identifier $productIdentifier not found") }
        return DTOConverter.convert(product)
    }

    fun saveProduct(dto: ProductDTO): ProductDTO {
        val product = productRepository.save(Product.convert(dto))
        return DTOConverter.convert(product)
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
        return DTOConverter.convert(
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
        return products.map { DTOConverter.convert(it) }
    }

    private fun findProductByIdOrThrow(productId: Long): Product =
        productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException("Product with id $productId not found") }
}
