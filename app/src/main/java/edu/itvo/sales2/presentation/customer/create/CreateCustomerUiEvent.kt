package edu.itvo.sales2.presentation.customer.create

sealed interface CreateCustomerUiEvent {
    data class idChanged(val value: String) : CreateCustomerUiEvent
    data class nameChanged(val value: String) : CreateCustomerUiEvent
    data class emailChanged(val value: String) : CreateCustomerUiEvent
    object SaveClicked : CreateCustomerUiEvent

}