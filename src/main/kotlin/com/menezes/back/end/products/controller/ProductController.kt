package com.menezes.back.end.products.controller

import com.menezes.back.end.products.dto.ProductDTO
import com.menezes.back.end.products.service.ProductService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping
    fun getProducts(): ResponseEntity<List<ProductDTO>> {
        return ResponseEntity.ok(productService.getAllProducts())
    }

    @GetMapping("/categories/{categoryId}")
    fun getProductsByCategory(
        @PathVariable categoryId: Long,
    ): ResponseEntity<List<ProductDTO>> {
        val product = productService.getProductByCategoryId(categoryId)
        return ResponseEntity.ok(product)
    }

    @GetMapping("/{productIdentifier}")
    fun findByIdentifier(
        @PathVariable productIdentifier: String,
    ): ResponseEntity<ProductDTO> {
        val product = productService.findByProductIdentifier(productIdentifier)
        return ResponseEntity.ok(product)
    }

    @PostMapping
    fun createProduct(
        @Valid @RequestBody dto: ProductDTO,
    ): ResponseEntity<ProductDTO> {
        val product = productService.saveProduct(dto)
        val location =
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(product.productIdentifier)
                .toUri()
        return ResponseEntity.created(location).body(product)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): ResponseEntity.HeadersBuilder<*> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent()
    }

    @PutMapping("/{id}")
    fun editProduct(
        @PathVariable id: Long,
        @RequestBody dto: ProductDTO,
    ): ResponseEntity.HeadersBuilder<*> {
        productService.editProduct(id, dto)
        return ResponseEntity.noContent()
    }

    @GetMapping("/pageable")
    fun getProductsPaginated(pageable: Pageable): ResponseEntity<Page<ProductDTO>> {
        val products = productService.getAllProductsPaged(pageable)
        return ResponseEntity.ok(products)
    }
}
