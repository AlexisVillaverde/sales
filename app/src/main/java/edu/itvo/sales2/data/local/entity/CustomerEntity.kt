package edu.itvo.sales2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(

    @PrimaryKey
    val id: String,

    val name:String,

    val email:String,
)

