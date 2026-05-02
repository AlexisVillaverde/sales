package edu.itvo.sales2.presentation.customer.update

sealed interface UpdateCustomerUiEvent {

    data class LoadCustomer(val id: String) : UpdateCustomerUiEvent
    data class idChanged(val value: String) : UpdateCustomerUiEvent
    data class nameChanged(val value: String) : UpdateCustomerUiEvent
    data class emailChanged(val value: String) : UpdateCustomerUiEvent
    object SaveClicked : UpdateCustomerUiEvent

}