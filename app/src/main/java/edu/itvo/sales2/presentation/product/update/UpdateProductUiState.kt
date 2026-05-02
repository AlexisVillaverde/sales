package edu.itvo.sales2.presentation.product.update

data class UpdateProductUiState (
    val code: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    val taxable: Boolean = true,
    val isLoading: Boolean = false
)