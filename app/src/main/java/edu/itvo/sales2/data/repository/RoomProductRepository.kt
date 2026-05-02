package edu.itvo.sales2.data.repository

import edu.itvo.sales2.data.local.dao.ProductDao
import edu.itvo.sales2.data.local.mapper.toDomain
import edu.itvo.sales2.data.local.mapper.toEntity
import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
class RoomProductRepository @Inject constructor(
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> {
        return dao.getProducts()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun findProductByCode(productCode: String): Product? {
        return dao.findByCode(productCode)?.toDomain()
    }

    override suspend fun updateProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun saveProduct(product: Product) {
        dao.insert(product.toEntity())
    }

    override suspend fun deleteProduct(productCode: String) {
        dao.deleteByCode(productCode)
    }
}