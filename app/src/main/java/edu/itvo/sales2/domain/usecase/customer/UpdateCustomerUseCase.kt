package edu.itvo.sales2.domain.usecase.customer

import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import javax.inject.Inject

class UpdateCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
){
    suspend operator fun invoke(customer: Customer){
        val existing = repository.findCustomerById(customerId = customer.id)

        require(existing!=null){
            "Customer with id: ${customer.id} does not exist"
        }

        repository.updateCustomer(customer)
    }
}