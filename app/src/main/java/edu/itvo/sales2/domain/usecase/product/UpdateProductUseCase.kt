package edu.itvo.sales2.domain.usecase.product

import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import javax.inject.Inject

class UpdateProductUseCase  @Inject constructor(
    private val repository: ProductRepository
){
    suspend operator fun invoke(product: Product){
        val existing = repository.findProductByCode(productCode = product.code)

        require(existing!=null){
            "Customer with id: ${product.code} does not exist"
        }

        repository.updateProduct(product)

    }
}