package edu.itvo.sales2.presentation.product.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.presentation.product.delete.DeleteProductUiEvent
import edu.itvo.sales2.presentation.product.delete.DeleteProductViewModel

@Composable
fun ListProduct(
    products: List<Product>,
    viewModel: DeleteProductViewModel = hiltViewModel()
) {


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = products,
            key = { it.code }
        ) { product ->
            ProductItem(product = product,
                onDelete = { producto ->
                    // Lógica para eliminar del repositorio o ViewModel
                    viewModel.onEvent(
                        DeleteProductUiEvent.DeleteClicked(producto.code)
                    )
                })
        }
    }
}