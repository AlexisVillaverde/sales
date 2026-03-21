package edu.itvo.sales2.domain.usecase.product

import edu.itvo.sales2.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase  @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productCode: String) {
        val existing = repository.findProductByCode(productCode)

        require(existing != null) {
            "Product with code: $productCode does not exist"
        }

        repository.deleteProduct(productCode)

    }
}