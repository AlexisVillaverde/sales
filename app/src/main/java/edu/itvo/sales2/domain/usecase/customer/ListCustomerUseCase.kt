package edu.itvo.sales2.domain.usecase.customer

import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    operator fun invoke(): Flow<List<Customer>> {
        return repository.getCustomers()
    }
}