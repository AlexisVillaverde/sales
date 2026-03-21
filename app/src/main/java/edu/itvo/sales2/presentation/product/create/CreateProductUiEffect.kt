package edu.itvo.sales2.presentation.product.create

sealed interface CreateProductUiEffect {
    object NavigateBack : CreateProductUiEffect

    data class ShowError(val message: String) : CreateProductUiEffect

    data class ShowSuccess(val message: String) : CreateProductUiEffect
}