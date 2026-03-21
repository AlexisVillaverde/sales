package edu.itvo.sales2.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseInMemoryRepository<T, ID>(
    initialData: List<T>
) : BaseRepository<T, ID> {

    protected val state = MutableStateFlow(initialData)
    abstract fun getId(item: T): ID
    override fun observeAll(): Flow<List<T>> = state
    override suspend fun findById(id: ID): T? {

        return state.value.find {
            getId(it) == id
        }
    }

    override suspend fun save(item: T) {

        state.update { current ->

            if (current.any { getId(it) == getId(item) }) {
                current
            } else {
                current + item
            }
        }
    }

    override suspend fun deleteById(id: ID) {

        state.update { current ->

            current.filter {
                getId(it) != id
            }
        }
    }
}
