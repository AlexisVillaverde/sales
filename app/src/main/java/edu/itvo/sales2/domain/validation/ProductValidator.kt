package edu.itvo.sales2.domain.validation

import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.presentation.product.ValidationResult

class ProductValidator {

    operator fun invoke(product: Product): ValidationResult =
        listOfNotNull(
            "Code required".takeIf { product.code.isBlank() },
            "Description required".takeIf { product.description.isBlank() },
            "Category required".takeIf { product.category.isBlank() },
            "Invalid price".takeIf { product.price < 0 },
            "Invalid Stock".takeIf { product.stock <= 0 }
        ).firstOrNull()
            ?.let { ValidationResult.Error(it) }
            ?: ValidationResult.Success
}