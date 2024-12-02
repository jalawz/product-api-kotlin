package com.menezes.back.end.products.advice

import java.time.Instant

data class BaseError(
    val message: String,
    val statusCode: Int,
    val timestamp: Instant,
)
