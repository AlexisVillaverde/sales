package edu.itvo.sales2.presentation.product.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ListProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ListProductViewModel = hiltViewModel(),
    onNavigateToUpdate: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.products.isEmpty() -> {
            EmptyProductsView()
        }

        else -> {
            ListProduct(products = uiState.products, onNavigateToUpdate = onNavigateToUpdate)
        }
    }
}