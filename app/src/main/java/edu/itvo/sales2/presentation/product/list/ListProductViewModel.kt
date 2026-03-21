package edu.itvo.sales2.presentation.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.usecase.product.ListProductsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListProductViewModel @Inject constructor(
    getProductsUseCase: ListProductsUseCase
) : ViewModel() {

    val uiState: StateFlow<ListProductUiState> =
        getProductsUseCase()
            .map { products ->
                ListProductUiState(
                    isLoading = false,
                    products = products
                )
            }
            .onStart {
                emit(ListProductUiState(isLoading = true))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListProductUiState()
            )
}

data class ListProductUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList()
)