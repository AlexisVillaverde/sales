package edu.itvo.sales2.presentation.customer.create

sealed interface CreateCustomerUiEffect {
    object NavigateBack : CreateCustomerUiEffect

    data class ShowError(val message: String) : CreateCustomerUiEffect
    data class ShowSuccess(val message: String) : CreateCustomerUiEffect

}