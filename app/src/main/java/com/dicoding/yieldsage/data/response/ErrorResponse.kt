package com.dicoding.yieldsage.data.response

data class ErrorResponse(
    val status: Int,
    val message: String,
    val errors: Boolean,
    val data: Any?
)
