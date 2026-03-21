package edu.itvo.sales2.data.local.repository

import kotlinx.coroutines.flow.Flow

interface BaseRepository<T, ID> {

    fun observeAll(): Flow<List<T>>
    suspend fun findById(id: ID): T?
    suspend fun save(item: T)
    suspend fun deleteById(id: ID)
}
