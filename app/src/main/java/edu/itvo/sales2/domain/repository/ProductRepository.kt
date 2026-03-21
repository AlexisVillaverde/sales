package edu.itvo.sales2.domain.repository

import edu.itvo.sales2.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun saveProduct(product: Product)
    suspend fun deleteProduct(productCode: String)
    suspend fun findProductByCode(productCode: String): Product?
    fun getProducts(): Flow<List<Product>>
}