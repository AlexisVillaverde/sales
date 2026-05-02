package edu.itvo.sales2.data.remote.api

import edu.itvo.sales2.data.remote.dto.CustomerDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerApiService {
    @GET("customers")
    suspend fun getCustomers(): ApiResponse<List<CustomerDto>>

    @GET("customers/{id}")
    suspend fun findCustomerById(
        @Path("id") code: String
    ): CustomerDto

    @POST("customers")
    suspend fun saveCustomer(
        @Body customer: CustomerDto
    ): ApiResponse<CustomerDto>

    @PUT("customers/{id}")
    suspend fun updateCustomer (
        @Path("id") id: String,
        @Body customer: CustomerDto): ApiResponse<CustomerDto>

    @DELETE("customers/{id}")
    suspend fun deleteCustomer( @Path("id") id: String): ApiResponse<CustomerDto>
}

