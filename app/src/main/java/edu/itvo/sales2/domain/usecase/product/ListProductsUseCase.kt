package edu.itvo.sales2.domain.usecase.product

import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    operator fun invoke(): Flow<List<Product>> {
        return repository.getProducts()
    }
}