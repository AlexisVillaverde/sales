package edu.itvo.sales2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import edu.itvo.sales2.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers")
    fun getCustomers(): Flow<List<CustomerEntity>>

    @Query ("SELECT * FROM customers WHERE id = :id")
    suspend fun findById(id: String): CustomerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: CustomerEntity)

    @Query ("DELETE FROM customers WHERE id = :id")
    suspend fun deleteById(id: String)

    @Update
    suspend fun updateCustomer(customer: CustomerEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(customers: List<CustomerEntity>)

    @Query("DELETE FROM customers")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(products: List<CustomerEntity>) {
        clearAll()
        insertAll(products)
    }

}