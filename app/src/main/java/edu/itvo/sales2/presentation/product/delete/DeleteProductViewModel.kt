package edu.itvo.sales2.presentation.product.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.usecase.product.DeleteProductUseCase
import edu.itvo.sales2.domain.usecase.product.ListProductsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteProductViewModel @Inject constructor(
    private val deleteProductUseCase: DeleteProductUseCase,
    private val listProductsUseCase: ListProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DeleteProductUiState(isLoading = true, products = emptyList()))
    val state: StateFlow<DeleteProductUiState> = _state

    private val _effect = Channel<DeleteProductUiEffect>()
    val effect = _effect.receiveAsFlow()

    private fun updateState(update: DeleteProductUiState.() -> DeleteProductUiState) {
        _state.update(update)
    }

    fun onEvent(event: DeleteProductUiEvent) {
        when (event) {
            is DeleteProductUiEvent.DeleteClicked -> deleteProduct(event.productId)
        }
    }

    private fun deleteProduct(productId: String) {

        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                deleteProductUseCase(productId)
                listProductsUseCase().collect { products ->
                    //Log.d("PRODUCTS", it.joinToString("\n"))
                    updateState { copy(products = products) }
                }
                sendEffect(DeleteProductUiEffect.ShowSuccess("Producto eliminado..."))
                delay(1000)
                sendEffect(DeleteProductUiEffect.NavigateBack)
            } catch (e: Exception) {
                sendEffect(
                    DeleteProductUiEffect.ShowError(
                        e.message ?: "Error al eliminar producto"
                    )
                )
            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: DeleteProductUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}