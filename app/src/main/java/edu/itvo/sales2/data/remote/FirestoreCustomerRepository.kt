package edu.itvo.sales2.data.remote

import edu.itvo.sales2.data.remote.datasource.CustomerFirebaseDataSource
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class FirestoreCustomerRepository @Inject constructor(
    private val firebaseDataSource: CustomerFirebaseDataSource
) : CustomerRepository {
    override suspend fun saveCustomer(customer: Customer) {
        firebaseDataSource.saveCustomer(customer)
    }

    override suspend fun findCustomerById(customerId: String): Customer? {
        return firebaseDataSource.findCustomerByCode(customerId)
    }

    override suspend fun deleteCustomer(customerId: String) {
        firebaseDataSource.deleteCustomer(customerId)
    }

    override fun getCustomers(): Flow<List<Customer>> {
        return firebaseDataSource.getCustomers()
    }

    override suspend fun updateCustomer(customer: Customer) {
        //firebaseDataSource.updateCustomer(customer)
    }



}