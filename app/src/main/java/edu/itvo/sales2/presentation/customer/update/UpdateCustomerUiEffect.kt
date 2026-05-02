package edu.itvo.sales2.presentation.customer.update

sealed interface UpdateCustomerUiEffect {
    object NavigateBack : UpdateCustomerUiEffect

    data class ShowError(val message: String) : UpdateCustomerUiEffect
    data class ShowSuccess(val message: String) : UpdateCustomerUiEffect

}