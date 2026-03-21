package edu.itvo.sales2.presentation.product.create

data class CreateProductUiState(
    val code: String = "",
    val description: String = "",
    val category: String = "",
    val price: String = "",
    val stock: String = "",
    val taxable: Boolean = true,
    val isLoading: Boolean = false
)



