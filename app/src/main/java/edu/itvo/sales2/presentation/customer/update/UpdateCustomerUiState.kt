package edu.itvo.sales2.presentation.customer.update

data class UpdateCustomerUiState (
    val id : String ="",
    val name : String ="",
    val email : String ="",
    val isLoading : Boolean = false
)