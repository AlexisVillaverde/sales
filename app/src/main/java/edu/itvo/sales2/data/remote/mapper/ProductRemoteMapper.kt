package edu.itvo.sales2.data.remote.mapper

import edu.itvo.sales2.data.local.entity.ProductEntity
import edu.itvo.sales2.data.remote.dto.ProductDto
import edu.itvo.sales2.domain.model.Product

object ProductRemoteMapper {

    fun ProductDto.toDomain(): Product = Product(
        code, description, category, price, stock, taxable
    )

    fun ProductDto.toEntity(): ProductEntity = ProductEntity(
        code, description, category, price, stock, taxable
    )

    fun Product.toDto(): ProductDto = ProductDto(
        code, description, category, price, stock, taxable
    )
}