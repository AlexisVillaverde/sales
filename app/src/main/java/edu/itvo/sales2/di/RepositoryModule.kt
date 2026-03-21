package edu.itvo.sales2.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.itvo.sales2.data.local.repository.InMemoryCustomerRepository
import edu.itvo.sales2.data.local.repository.RoomCustomerRepository
import edu.itvo.sales2.data.local.repository.RoomProductRepository
import edu.itvo.sales2.data.remote.FirestoreCustomerRepository
import edu.itvo.sales2.data.remote.FirestoreProductRepository
import edu.itvo.sales2.domain.repository.CustomerRepository
import edu.itvo.sales2.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        repository: FirestoreProductRepository //RoomProductRepository //InMemoryProductRepository
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
        repository: FirestoreCustomerRepository //RoomCustomerRepository //InMemoryCustomerRepository
    ): CustomerRepository
}
