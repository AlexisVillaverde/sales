package edu.itvo.sales2.presentation.product.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.usecase.product.CreateProductUseCase
import edu.itvo.sales2.domain.usecase.product.ListProductsUseCase
import edu.itvo.sales2.domain.validation.ProductValidator
import edu.itvo.sales2.presentation.product.ValidationResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase,
    private val listProductsUseCase: ListProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateProductUiState())
    val state: StateFlow<CreateProductUiState> = _state

    private val _effect = Channel<CreateProductUiEffect>()
    val effect = _effect.receiveAsFlow()

    private fun updateState(update: CreateProductUiState.() -> CreateProductUiState) {
        _state.update(update)
    }

    fun onEvent(event: CreateProductUiEvent) {

        when (event) {

            is CreateProductUiEvent.CodeChanged ->
                updateState { copy(code = event.value) }

            is CreateProductUiEvent.DescriptionChanged ->
                updateState { copy(description = event.value) }

            is CreateProductUiEvent.CategoryChanged ->
                updateState { copy(category = event.value) }

            is CreateProductUiEvent.PriceChanged ->
                updateState { copy(price = event.value) }

            is CreateProductUiEvent.StockChanged ->
                updateState { copy(stock = event.value) }

            is CreateProductUiEvent.TaxableChanged ->
                updateState { copy(taxable = event.value) }

            CreateProductUiEvent.SaveClicked ->
                saveProduct()
        }
    }


    private fun saveProduct() {

        val currentState = state.value

        val price = currentState.price.toDouble()
        val stock = currentState.stock.toInt()


        viewModelScope.launch {

            updateState { copy(isLoading = true) }

            try {

                val product = Product(
                    code = currentState.code,
                    description = currentState.description,
                    category = currentState.category,
                    price = price,
                    stock = stock,
                    taxable = currentState.taxable
                )

                val result = ProductValidator().invoke(product)
                when(result) {
                    is ValidationResult.Success -> {
                        createProductUseCase(product)
                        listProductsUseCase().collect {
                            Log.d("PRODUCTS", it.joinToString("\n"))
                        }
                        sendEffect(CreateProductUiEffect.ShowSuccess("Producto agregado..."))
                        delay(1000)
                        sendEffect(CreateProductUiEffect.NavigateBack)
                    }
                    is ValidationResult.Error -> {
                        sendEffect(
                            CreateProductUiEffect.ShowError(result.message)
                        )
                    }
                }
                createProductUseCase(product)



            } catch (e: Exception) {

                sendEffect(
                    CreateProductUiEffect.ShowError(
                        e.message ?: "Error desconocido"
                    )
                )

            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private fun sendEffect(effect: CreateProductUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}



