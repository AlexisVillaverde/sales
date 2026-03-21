package edu.itvo.sales2.data.mapper

import edu.itvo.sales2.data.local.entity.ProductEntity
import edu.itvo.sales2.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        code,
        description,
        category,
        price,
        stock,
        taxable
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        code,
        description,
        category,
        price,
        stock,
        taxable
    )
}