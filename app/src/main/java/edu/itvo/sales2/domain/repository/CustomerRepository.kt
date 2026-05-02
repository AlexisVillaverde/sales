package edu.itvo.sales2.domain.repository

import edu.itvo.sales2.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun saveCustomer(customer: Customer)

    suspend fun findCustomerById(customerId: String): Customer?

    suspend fun deleteCustomer(customerId: String)

    suspend fun updateCustomer(customer: Customer)
    fun getCustomers(): Flow<List<Customer>>

}