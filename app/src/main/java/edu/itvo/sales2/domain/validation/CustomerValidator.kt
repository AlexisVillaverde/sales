package edu.itvo.sales2.domain.validation

import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.presentation.product.ValidationResult
import kotlin.collections.firstOrNull
import kotlin.let
import kotlin.takeIf
import kotlin.text.isBlank

class CustomerValidator {
    operator fun invoke(customer: Customer): ValidationResult =
        listOfNotNull(
            "Id required".takeIf { customer.id.isBlank() },
            "Name required".takeIf { customer.name.isBlank() },
            "Email required".takeIf { customer.email.isBlank() }
        ).firstOrNull(
            )?.let { ValidationResult.Error(it) }
            ?: ValidationResult.Success

}