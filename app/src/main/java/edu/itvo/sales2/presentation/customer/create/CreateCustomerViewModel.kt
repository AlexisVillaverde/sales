package edu.itvo.sales2.presentation.customer.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.usecase.customer.CreateCustomerUseCase
import edu.itvo.sales2.domain.usecase.customer.ListCustomerUseCase
import edu.itvo.sales2.domain.validation.CustomerValidator
import edu.itvo.sales2.presentation.product.ValidationResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateCustomerViewModel @Inject constructor(
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val listCustomersUseCase: ListCustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateCustomerUiState())
    val state: StateFlow<CreateCustomerUiState> = _state

    private val _effect = Channel<CreateCustomerUiEffect>()

    val effect = _effect.receiveAsFlow()

    private fun updateState(update: CreateCustomerUiState.() -> CreateCustomerUiState) {
        _state.update(update)
    }

    fun onEvent(event: CreateCustomerUiEvent){
        when(event){
            is CreateCustomerUiEvent.idChanged -> updateState { copy(id = event.value) }
            is CreateCustomerUiEvent.nameChanged -> updateState { copy(name = event.value) }
            is CreateCustomerUiEvent.emailChanged -> updateState { copy(email = event.value) }

            CreateCustomerUiEvent.SaveClicked ->
                saveCustomer()

        }
    }
    private fun saveCustomer() {
        val currentState = state.value
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                val customer = Customer(
                    id = currentState.id,
                    name = currentState.name,
                    email = currentState.email
                )
                val result = CustomerValidator().invoke(customer)
                when (result) {
                    is ValidationResult.Success -> {
                        createCustomerUseCase(customer)
                        listCustomersUseCase().collect {
                            Log.d("CUSTOMERS", it.joinToString("\n"))
                        }
                        sendEffect(CreateCustomerUiEffect.ShowSuccess("Cliente agregado..."))
                        delay(1000)
                        sendEffect(CreateCustomerUiEffect.NavigateBack)
                    }
                    is ValidationResult.Error -> {
                        sendEffect(CreateCustomerUiEffect.ShowError(result.message))
                    }
                }
            }
            catch (e: Exception){
                sendEffect(
                    CreateCustomerUiEffect.ShowError(
                        e.message ?: "Error desconocido"
                    )
                )
            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }
    private fun sendEffect(effect:  CreateCustomerUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }


}