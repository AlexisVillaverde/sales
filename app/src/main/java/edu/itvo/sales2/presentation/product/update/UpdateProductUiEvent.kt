package edu.itvo.sales2.presentation.product.update

import edu.itvo.sales2.presentation.product.create.CreateProductUiEvent

sealed interface UpdateProductUiEvent {

    data class LoadProduct(val id: String) : UpdateProductUiEvent
    data class CodeChanged(val value: String) : UpdateProductUiEvent
    data class DescriptionChanged(val value: String) : UpdateProductUiEvent
    data class CategoryChanged(val value: String) : UpdateProductUiEvent
    data class PriceChanged(val value: Double) : UpdateProductUiEvent
    data class StockChanged(val value: Int) : UpdateProductUiEvent
    data class TaxableChanged(val value: Boolean) : UpdateProductUiEvent
    object SaveClicked : UpdateProductUiEvent
}