package edu.itvo.sales2.domain.usecase.customer

import edu.itvo.sales2.domain.repository.CustomerRepository
import javax.inject.Inject

class DeleteCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(customerId: String){
        val existing = repository.findCustomerById(customerId)

        require(existing!=null){
            "Customer with id: $customerId does not exist"
        }
    }
}