package edu.itvo.sales2.data.remote.dto

data class ProductDto (
    val code: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val taxable: Boolean
)
