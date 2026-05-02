package edu.itvo.sales2.data.repository

import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryProductRepository @Inject constructor()
    : BaseInMemoryRepository<Product, String>(
    initialData = listOf(
        Product("P1", "Laptop", "Electronics", 15000.0, 10, true),
        Product("P2", "Mouse", "Electronics", 500.0, 50, true),
        Product("P3", "Desk", "Furniture", 3000.0, 5, false)
    )
),
    ProductRepository {

    override fun getId(item: Product): String = item.code

    override fun getProducts(): Flow<List<Product>> = observeAll()

    override fun observeAll(): Flow<List<Product>> = state

    override suspend fun findProductByCode(productCode: String): Product? {
        return findById(productCode)
    }

    override suspend fun saveProduct(product: Product) {
        save(product)
    }

    override suspend fun deleteProduct(productCode: String) {
        deleteById(productCode)
    }

    override suspend fun updateProduct(product: Product) {
        TODO("Not yet implemented")
    }
}
