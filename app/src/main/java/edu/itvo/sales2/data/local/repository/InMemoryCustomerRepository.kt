package edu.itvo.sales2.data.local.repository


import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class InMemoryCustomerRepository @Inject constructor()
    : BaseInMemoryRepository<Customer, String>(
    initialData = listOf(
        Customer("1", "Alexis", "alexis@itvo")

    )
),
    CustomerRepository {

    override fun getId(item: Customer) = item.id

    override fun getCustomers() = observeAll()

    override suspend fun findCustomerById(customerId: String)
            = findById(customerId)

    override suspend fun saveCustomer(customer: Customer)
            = save(customer)

    override suspend fun deleteCustomer(customerId: String)
            = deleteById(customerId)
}
