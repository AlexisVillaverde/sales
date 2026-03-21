package edu.itvo.sales2.presentation.customer.delete

sealed class DeleteCustomerUiEvent {
    data class DeleteClicked(val customerId: String) : DeleteCustomerUiEvent()
}