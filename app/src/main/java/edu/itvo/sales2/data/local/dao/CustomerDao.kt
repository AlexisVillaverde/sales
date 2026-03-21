package edu.itvo.sales2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.itvo.sales2.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers")
    fun getCustomers(): Flow<List<CustomerEntity>>

    @Query ("SELECT * FROM customers WHERE id = :id")
    suspend fun findById(id: String): CustomerEntity?

    @Query ("DELETE FROM customers WHERE id = :id")
    suspend fun deleteById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: CustomerEntity)
}