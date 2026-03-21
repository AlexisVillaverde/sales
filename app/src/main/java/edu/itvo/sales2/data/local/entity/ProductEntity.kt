package edu.itvo.sales2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(

    @PrimaryKey
    val code: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val taxable: Boolean
)