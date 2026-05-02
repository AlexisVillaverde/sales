package edu.itvo.sales2.data.repository

import android.util.Log
import edu.itvo.sales2.data.local.datasource.CustomerLocalDataSource
import edu.itvo.sales2.data.local.mapper.toDomain
import edu.itvo.sales2.data.local.mapper.toEntity
import edu.itvo.sales2.data.remote.datasource.CustomerRemoteDataSource
import edu.itvo.sales2.data.remote.mapper.CustomerRemoteMapper.toEntity
import edu.itvo.sales2.data.remote.mapper.CustomerRemoteMapper.toDomain
import edu.itvo.sales2.data.remote.mapper.CustomerRemoteMapper.toDto
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val remote: CustomerRemoteDataSource,
    private val local: CustomerLocalDataSource
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> = flow {

        // 🔥 1. Intentar actualizar desde API
        try {
            val customers = remote.getCustomers().data
                .map { it.toDomain() }

            Log.d(
                "customers",
                customers.joinToString(separator = "\n") { customer ->
                    "id=${customer.id}, name=${customer.name}, email=${customer.email}"
                }
            )

            local.replaceAll(customers.map { it.toEntity() })

        } catch (e: Exception) {
            Log.e("CUSTOMERS_ERROR", e.message ?: "Unknown error", e)
        }

        // 🔥 2. Emitir datos locales (flow infinito)
        emitAll(
            local.getCustomers()
                .map { list -> list.map { it.toDomain() } }
        )
    }

    override suspend fun findCustomerById(customerId: String): Customer? {

        // 1. Buscar local primero
        val localCustomer = local.findCustomerByCode(customerId)
        if (localCustomer != null) {
            return localCustomer.toDomain()
        }

        // 2. Si no existe, buscar remoto
        return try {
            val remoteCustomer = remote.findCustomerByCode(customerId)

            // guardar en local
            local.saveCustomer(remoteCustomer.toEntity())

            remoteCustomer.toDomain()

        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveCustomer(customer: Customer)  {
        try {
            remote.saveCustomer(customer.toDto())
            local.saveCustomer(customer.toEntity())
        } catch (e: Exception) {
            local.saveCustomer(customer.toEntity())
        }
    }

    override suspend fun deleteCustomer(customerId: String) {
        try {
            remote.deleteCustomer(customerId)
            local.deleteCustomer(customerId)
        }catch (e: Exception){
            local.deleteCustomer(customerId)
        }
    }

    override suspend fun updateCustomer(customer: Customer) {
        try {
            remote.updateCustomer(customer.toDto())
            local.updateCustomer(customer.toEntity())
        } catch (e: Exception) {
            local.updateCustomer(customer.toEntity())
        }
    }
}


