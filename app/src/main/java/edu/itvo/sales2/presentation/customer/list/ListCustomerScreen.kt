package edu.itvo.sales2.presentation.customer.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ListCustomerScreen(
    modifier: Modifier= Modifier,
    viewModel: ListCustomerViewModel = hiltViewModel()
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

        uiState.customers.isEmpty() -> {
            EmptyProductsView()
        }

        else -> {
            ListCustomer(customers = uiState.customers)
        }





    }
}
