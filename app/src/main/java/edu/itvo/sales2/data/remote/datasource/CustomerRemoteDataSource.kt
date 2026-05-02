package edu.itvo.sales2.data.remote.datasource

import edu.itvo.sales2.data.remote.api.ApiResponse
import edu.itvo.sales2.data.remote.api.CustomerApiService
import edu.itvo.sales2.data.remote.dto.CustomerDto
import javax.inject.Inject

class CustomerRemoteDataSource @Inject constructor(
    private val api: CustomerApiService
) {

    suspend fun findCustomerByCode(id: String): CustomerDto {
        return api.findCustomerById(id)
    }

    suspend fun saveCustomer(customer: CustomerDto): ApiResponse<CustomerDto> {
        return api.saveCustomer(customer)
    }

    suspend fun getCustomers(): ApiResponse<List<CustomerDto>> {
        return api.getCustomers()
    }

    suspend fun deleteCustomer(id: String): ApiResponse<CustomerDto> {
        return api.deleteCustomer(id)
    }

    suspend fun updateCustomer(customer: CustomerDto): ApiResponse<CustomerDto> {
        return api.updateCustomer(customer.id, customer)
    }
}