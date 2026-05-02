package edu.itvo.sales2.data.repository

import edu.itvo.sales2.data.local.dao.CustomerDao
import edu.itvo.sales2.data.local.mapper.toDomain
import edu.itvo.sales2.data.local.mapper.toEntity
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomCustomerRepository @Inject constructor(
    private val dao: CustomerDao
) : CustomerRepository{
    override suspend fun saveCustomer(customer: Customer) {
        dao.insert(customer.toEntity())
    }

    override suspend fun deleteCustomer(customerId: String) {
        dao.deleteById(customerId)
    }

    override suspend fun updateCustomer(customer: Customer) {
        TODO("Not yet implemented")
    }

    override suspend fun findCustomerById(customerId: String): Customer? {
        return dao.findById(customerId)?.toDomain()
    }

    override fun getCustomers(): Flow<List<Customer>> {
        return dao.getCustomers()
            .map {
                    list -> list.map { it.toDomain() }
            }
    }
}