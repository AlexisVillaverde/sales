package edu.itvo.sales2.domain.usecase.customer

import edu.itvo.sales2.domain.repository.CustomerRepository
import edu.itvo.sales2.domain.model.Customer
import javax.inject.Inject

class CreateCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(customer: Customer) {
        val existing = repository.findCustomerById(customerId = customer.id)

        require(existing == null) {
            "Customer with id: ${customer.id} already exists"
        }
        repository.saveCustomer(customer)
    }
}

