package edu.itvo.sales2.domain.usecase.product

import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: ProductRepository
){
    suspend operator fun invoke(product: Product){
        val existing = repository.findProductByCode(productCode = product.code)

        require(existing==null){
            "Product with code: ${product.code} already exists"
        }

        repository.saveProduct(product)
    }
}