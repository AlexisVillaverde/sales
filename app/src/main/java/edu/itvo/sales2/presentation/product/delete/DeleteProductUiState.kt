package edu.itvo.sales2.presentation.product.delete

import edu.itvo.sales2.domain.model.Product

data class DeleteProductUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val products: List<Product>
)