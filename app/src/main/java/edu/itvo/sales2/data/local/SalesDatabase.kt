package edu.itvo.sales2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.itvo.sales2.data.local.dao.CustomerDao
import edu.itvo.sales2.data.local.dao.ProductDao
import edu.itvo.sales2.data.local.entity.CustomerEntity
import edu.itvo.sales2.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, CustomerEntity::class],
    version = 2,
    exportSchema = false
)
abstract class SalesDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun customerDao(): CustomerDao
}
