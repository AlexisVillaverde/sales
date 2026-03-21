package edu.itvo.sales2.presentation.product.delete

sealed class DeleteProductUiEvent {
    data class DeleteClicked(val productId: String) : DeleteProductUiEvent()
}