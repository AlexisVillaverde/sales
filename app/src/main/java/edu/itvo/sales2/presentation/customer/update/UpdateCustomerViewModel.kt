package edu.itvo.sales2.presentation.customer.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.usecase.customer.CreateCustomerUseCase
import edu.itvo.sales2.domain.usecase.customer.GetCustomerUseCase
import edu.itvo.sales2.domain.usecase.customer.ListCustomerUseCase
import edu.itvo.sales2.domain.usecase.customer.UpdateCustomerUseCase
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
class UpdateCustomerViewModel @Inject constructor(
    private val updateCustomerUseCase: UpdateCustomerUseCase,
    private val getCustomerUseCase: GetCustomerUseCase,
    private val listCustomersUseCase: ListCustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UpdateCustomerUiState())
    val state: StateFlow<UpdateCustomerUiState> = _state

    private val _effect = Channel<UpdateCustomerUiEffect>()

    val effect = _effect.receiveAsFlow()

    private fun updateState(update: UpdateCustomerUiState.() -> UpdateCustomerUiState) {
        _state.update(update)
    }

    fun onEvent(event: UpdateCustomerUiEvent){
        when(event){
            is UpdateCustomerUiEvent.idChanged -> updateState { copy(id = event.value) }
            is UpdateCustomerUiEvent.nameChanged -> updateState { copy(name = event.value) }
            is UpdateCustomerUiEvent.emailChanged -> updateState { copy(email = event.value) }

            UpdateCustomerUiEvent.SaveClicked ->
                saveCustomer()

            is UpdateCustomerUiEvent.LoadCustomer -> loadCustomer(event.id)
        }
    }

    private fun loadCustomer(id: String) {
        val currentState = state.value
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            val customer = getCustomerUseCase(id)
                updateState{
                    copy(id = customer?.id ?: "",
                        name = customer?.name ?: "",
                        email = customer?.email ?: "",
                        isLoading = false)
                }
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
                        updateCustomerUseCase(customer)
                        listCustomersUseCase().collect {
                            Log.d("CUSTOMERS", it.joinToString("\n"))
                        }
                        sendEffect(UpdateCustomerUiEffect.ShowSuccess("Customer updated..."))
                        delay(1000)
                        sendEffect(UpdateCustomerUiEffect.NavigateBack)
                    }
                    is ValidationResult.Error -> {
                        sendEffect(UpdateCustomerUiEffect.ShowError(result.message))
                    }
                }
            }
            catch (e: Exception){
                sendEffect(
                    UpdateCustomerUiEffect.ShowError(
                        e.message ?: "Unknown error"
                    )
                )
            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }
    private fun sendEffect(effect:  UpdateCustomerUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }


}