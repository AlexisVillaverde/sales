package edu.itvo.sales2.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import edu.itvo.sales2.domain.model.Product
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProductFirebaseDataSource @Inject constructor() {

    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("products")

    fun getProducts(): Flow<List<Product>> = callbackFlow {

        val listener = collection.addSnapshotListener { snapshot, error ->

            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val products = snapshot?.documents?.mapNotNull {
                it.toObject(Product::class.java)
            } ?: emptyList()

            trySend(products)
        }

        awaitClose { listener.remove() }
    }

    suspend fun findProductByCode(productCode: String): Product? {
        val doc = collection.document(productCode).get().await()
        return doc.toObject(Product::class.java)
    }

    suspend fun saveProduct(product: Product) {
        collection.document(product.code).set(product).await()
    }

    suspend fun deleteProduct(productCode: String) {
        collection.document(productCode).delete().await()
    }
}