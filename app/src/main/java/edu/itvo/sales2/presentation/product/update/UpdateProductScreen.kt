package edu.itvo.sales2.presentation.product.update


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdateProductScreen(
    viewModel: UpdateProductViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    productCode: String
) {

    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(productCode) {
        viewModel.onEvent(
            UpdateProductUiEvent.LoadProduct(productCode)
        )
    }

        LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->

            when (effect) {

                UpdateProductUiEffect.NavigateBack ->
                    onNavigateBack()

                is UpdateProductUiEffect.ShowError ->
                    snackbarHostState.showSnackbar(effect.message)

                is UpdateProductUiEffect.ShowSuccess ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {innerPadding ->
        Column(
            modifier = Modifier.padding(16.dp)
                .padding(innerPadding) // Aplicar padding del sistema
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.code,
                onValueChange = { },
                label = { Text("Code") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.description,
                onValueChange = {
                    viewModel.onEvent(
                        UpdateProductUiEvent.DescriptionChanged(it)
                    )
                },
                label = { Text("Description") }
            )

            OutlinedTextField(
                value = state.category,
                onValueChange = {
                    viewModel.onEvent(
                        UpdateProductUiEvent.CategoryChanged(it)
                    )
                },
                label = { Text("Category") }
            )

            OutlinedTextField(
                value = state.price.toString(),
                onValueChange = {input->
                    val priceValue = input.toDoubleOrNull() ?: 0.0
                    viewModel.onEvent(
                        UpdateProductUiEvent.PriceChanged(priceValue)
                    )
                },
                label = { Text("Precio") }
            )

            OutlinedTextField(
                value = state.stock.toString(),
                onValueChange = { input ->
                    // Convertimos el String a Int. Si el campo está vacío o no es un número, usamos 0.
                    val stockValue = input.toIntOrNull() ?: 0
                    viewModel.onEvent(
                        UpdateProductUiEvent.StockChanged(stockValue)
                    )
                },
                label = { Text("Disponibilidad") }
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Aplica impuesto? ")

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Switch(
                        checked = state.taxable,
                        onCheckedChange = { isChecked ->

                            viewModel.onEvent(
                                UpdateProductUiEvent.TaxableChanged(isChecked)
                            )

                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        if (state.taxable)
                            "Si"
                        else
                            "No"
                    )

                }
            }

            Button(
                onClick = {
                    viewModel.onEvent(
                        UpdateProductUiEvent.SaveClicked
                    )
                    onNavigateBack()
                }
            ) {
                Text("Update Product")
            }
        }
    }
}
