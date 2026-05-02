package edu.itvo.sales2.presentation.product.update

sealed interface UpdateProductUiEffect {
    object NavigateBack : UpdateProductUiEffect

    data class ShowError(val message: String) : UpdateProductUiEffect
    data class ShowSuccess(val message: String) : UpdateProductUiEffect

}