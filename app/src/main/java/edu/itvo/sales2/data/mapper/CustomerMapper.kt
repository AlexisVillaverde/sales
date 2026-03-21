package edu.itvo.sales2.data.mapper

import edu.itvo.sales2.data.local.entity.CustomerEntity
import edu.itvo.sales2.domain.model.Customer


fun CustomerEntity.toDomain():Customer{
    return Customer(
        id,
        name,
        email
    )
}

fun Customer.toEntity():CustomerEntity{
    return CustomerEntity(
        id,
        name,
        email
    )
}
