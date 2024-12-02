package com.menezes.back.end.products.advice

import com.menezes.back.end.products.exceptions.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(ex: ProductNotFoundException): ResponseEntity<BaseError> {
        val baseError =
            BaseError(
                message = requireNotNull(ex.message),
                statusCode = HttpStatus.NOT_FOUND.value(),
                timestamp = Instant.now(),
            )

        return ResponseEntity(baseError, HttpStatus.NOT_FOUND)
    }
}
