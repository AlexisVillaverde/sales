package edu.itvo.sales2.data.local.datasource

import edu.itvo.sales2.data.local.dao.CustomerDao
import edu.itvo.sales2.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerLocalDataSource @Inject constructor(
    private val dao: CustomerDao
){
    fun getCustomers(): Flow<List<CustomerEntity>> {
        return dao.getCustomers()
    }
    suspend fun findCustomerByCode(customerId: String): CustomerEntity? {
        return dao.findById(customerId)
    }

    suspend fun saveCustomer(customer: CustomerEntity) {
        dao.insert(customer)
    }

    suspend fun deleteCustomer(customerId: String) {
        dao.deleteById(customerId)
    }

    suspend fun saveCustomers(customers: List<CustomerEntity>) {
        dao.insertAll(customers)
    }

    suspend fun updateCustomer(customer: CustomerEntity){
        dao.updateCustomer(customer)
    }

    suspend fun replaceAll(customers: List<CustomerEntity>){
        dao.replaceAll(customers)
    }
}