package edu.itvo.sales2.data.local.datasource

import edu.itvo.sales2.data.local.dao.ProductDao
import edu.itvo.sales2.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val dao: ProductDao
){
    fun getProducts(): Flow<List<ProductEntity>> {
        return dao.getProducts()
    }

    suspend fun findProductByCode(productCode: String): ProductEntity? {
        return dao.findByCode(productCode)
    }

    suspend fun saveProduct(product: ProductEntity) {
        dao.insert(product)
    }

    suspend fun deleteProduct(productCode: String) {
        dao.deleteByCode(productCode)
    }

    suspend fun saveProducts(products: List<ProductEntity>) {
        dao.insertAll(products)
    }
    suspend fun updateProduct(product: ProductEntity) {
        dao.updateProduct(product)
    }
    suspend fun replaceAll(products: List<ProductEntity>){
        dao.replaceAll(products)
    }
}