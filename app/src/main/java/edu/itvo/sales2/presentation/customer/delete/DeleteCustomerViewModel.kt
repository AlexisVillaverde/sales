package edu.itvo.sales2.presentation.customer.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.usecase.customer.DeleteCustomerUseCase
import edu.itvo.sales2.domain.usecase.customer.ListCustomerUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteCustomerViewModel @Inject constructor(
    private val deleteCustomerUseCase: DeleteCustomerUseCase,
    private val listCustomerUseCase: ListCustomerUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DeleteCustomerUiState(isLoading = true, customers = emptyList()))
    val state: StateFlow<DeleteCustomerUiState> = _state
    private val _effect = Channel<DeleteCustomerUiEffect>()
    val effect = _effect.receiveAsFlow()
    private fun updateState(update: DeleteCustomerUiState.() -> DeleteCustomerUiState) {
        _state.update(update)
    }
    fun onEvent(event: DeleteCustomerUiEvent) {
        when (event) {
            is DeleteCustomerUiEvent.DeleteClicked -> deleteCustomer (event.customerId)
        }
    }
    private fun deleteCustomer(customerId: String) {

        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                deleteCustomerUseCase(customerId = customerId)
                listCustomerUseCase().collect { customers ->
                    //Log.d("PRODUCTS", it.joinToString("\n"))
                    updateState { copy(customers = customers) }
                }
                sendEffect(DeleteCustomerUiEffect.ShowSuccess("Customer eliminado..."))
                delay(1000)
                sendEffect(DeleteCustomerUiEffect.NavigateBack)
            } catch (e: Exception) {
                sendEffect(
                    DeleteCustomerUiEffect.ShowError(
                        e.message ?: "Error al eliminar customer"
                    )
                )
            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: DeleteCustomerUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}