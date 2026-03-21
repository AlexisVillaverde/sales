package edu.itvo.sales2.data.remote

import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class FirestoreProductRepository @Inject constructor(
    private val firebaseDataSource: ProductFirebaseDataSource
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> {
        return firebaseDataSource.getProducts()
    }

    override suspend fun findProductByCode(productCode: String): Product? {
        return firebaseDataSource.findProductByCode(productCode)
    }

    override suspend fun saveProduct(product: Product) {
        firebaseDataSource.saveProduct(product)
    }

    override suspend fun deleteProduct(productCode: String) {
        firebaseDataSource.deleteProduct(productCode)
    }
}