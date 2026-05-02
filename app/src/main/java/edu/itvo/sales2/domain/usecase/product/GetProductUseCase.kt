package edu.itvo.sales2.domain.usecase.product

import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import javax.inject.Inject


class GetProductUseCase @Inject constructor(
private val repository: ProductRepository
){
    suspend operator fun invoke(productCode: String): Product? {
        val existing = repository.findProductByCode(productCode)

        require(existing!=null){
            "Customer with id: $productCode does not exist"
        }
        return existing
    }
}