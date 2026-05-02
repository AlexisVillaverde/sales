package edu.itvo.sales2.data.remote.mapper

import edu.itvo.sales2.data.local.entity.CustomerEntity
import edu.itvo.sales2.data.remote.dto.CustomerDto
import edu.itvo.sales2.domain.model.Customer

object CustomerRemoteMapper {
    fun CustomerDto.toDomain(): Customer = Customer(
        id, name, email
    )

    fun CustomerDto.toEntity(): CustomerEntity = CustomerEntity(
        id, name, email
    )

    fun Customer.toDto(): CustomerDto = CustomerDto(
        id, name, email
    )
}