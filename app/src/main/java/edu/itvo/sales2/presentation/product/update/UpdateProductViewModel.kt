package edu.itvo.sales2.presentation.product.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.usecase.customer.GetCustomerUseCase
import edu.itvo.sales2.domain.usecase.customer.ListCustomerUseCase
import edu.itvo.sales2.domain.usecase.customer.UpdateCustomerUseCase
import edu.itvo.sales2.domain.usecase.product.GetProductUseCase
import edu.itvo.sales2.domain.usecase.product.ListProductsUseCase
import edu.itvo.sales2.domain.usecase.product.UpdateProductUseCase
import edu.itvo.sales2.domain.validation.CustomerValidator
import edu.itvo.sales2.domain.validation.ProductValidator
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
class UpdateProductViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val listProductsUseCase: ListProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UpdateProductUiState())
    val state: StateFlow<UpdateProductUiState> = _state

    private val _effect = Channel<UpdateProductUiEffect>()

    val effect = _effect.receiveAsFlow()

    private fun updateState(update: UpdateProductUiState.() -> UpdateProductUiState) {
        _state.update(update)
    }

    fun onEvent(event: UpdateProductUiEvent){
        when(event){
            is UpdateProductUiEvent.CodeChanged -> updateState { copy(code = event.value) }
            is UpdateProductUiEvent.DescriptionChanged -> updateState { copy(description = event.value) }
            is UpdateProductUiEvent.CategoryChanged -> updateState { copy(category = event.value) }
            is UpdateProductUiEvent.PriceChanged -> updateState { copy(price = event.value) }
            is UpdateProductUiEvent.StockChanged -> updateState { copy(stock = event.value) }
            is UpdateProductUiEvent.TaxableChanged -> updateState { copy(taxable = event.value) }

            UpdateProductUiEvent.SaveClicked ->
                saveProduct()

            is UpdateProductUiEvent.LoadProduct -> loadProduct(event.id)
        }
    }

    private fun loadProduct(id: String) {
        val currentState = state.value
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            val product = getProductUseCase(id)
                updateState{
                    copy(code = product?.code ?: "",
                        description = product?.description ?: "",
                        category = product?.category ?: "",
                        price = product?.price ?: 0.0,
                        stock = product?.stock ?: 0,
                        taxable = product?.taxable ?: false,
                        isLoading = false)
                }
        }
    }
    private fun saveProduct() {
        val currentState = state.value
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                val product = Product(
                    code = currentState.code,
                    description = currentState.description,
                    category = currentState.category,
                    price = currentState.price,
                    stock = currentState.stock,
                    taxable = currentState.taxable
                )
                val result = ProductValidator().invoke(product)
                when (result) {
                    is ValidationResult.Success -> {
                        updateProductUseCase(product)
                        listProductsUseCase().collect {
                            Log.d("PRODUCTS", it.joinToString("\n"))
                        }
                        sendEffect(UpdateProductUiEffect.ShowSuccess("Product updated..."))
                        delay(1000)
                        sendEffect(UpdateProductUiEffect.NavigateBack)
                    }
                    is ValidationResult.Error -> {
                        sendEffect(UpdateProductUiEffect.ShowError(result.message))
                    }
                }
            }
            catch (e: Exception){
                sendEffect(
                    UpdateProductUiEffect.ShowError(
                        e.message ?: "Unknown error"
                    )
                )
            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }
    private fun sendEffect(effect:  UpdateProductUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }


}