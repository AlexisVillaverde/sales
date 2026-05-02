package edu.itvo.sales2.data.remote.api

data class ApiResponse<T>(
    val success : Boolean,
    val data: T
)