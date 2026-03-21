package edu.itvo.sales2.presentation.customer.delete

import edu.itvo.sales2.domain.model.Customer

data class DeleteCustomerUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val customers: List<Customer>
)