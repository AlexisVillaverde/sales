package edu.itvo.sales2.presentation.customer.delete

sealed interface DeleteCustomerUiEffect {
    data class ShowSuccess(val message: String) : DeleteCustomerUiEffect
    data class ShowError(val message: String) : DeleteCustomerUiEffect
    object NavigateBack : DeleteCustomerUiEffect
}