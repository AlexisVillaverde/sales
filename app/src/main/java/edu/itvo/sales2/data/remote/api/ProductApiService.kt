package edu.itvo.sales2.data.remote.api

import edu.itvo.sales2.data.remote.dto.ProductDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): ApiResponse<List<ProductDto>>

    @GET("products/{code}")
    suspend fun findProductByCode(
        @Path("code") code: String
    ): ProductDto

    @POST("products")
    suspend fun saveProduct(
        @Body product: ProductDto
    ):  ApiResponse<ProductDto>

    @PUT("products/{code}")
    suspend fun updateProduct (
        @Path("code") code: String,
        @Body product: ProductDto): ApiResponse<ProductDto>

    @DELETE("products/{code}")
    suspend fun deleteProduct (
        @Path("code") code: String): ApiResponse<ProductDto>

}