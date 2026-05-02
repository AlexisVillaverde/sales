package edu.itvo.sales2.presentation.product.create

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
fun CreateProductScreen(
    viewModel: CreateProductViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when (effect) {

                CreateProductUiEffect.NavigateBack ->
                    onNavigateBack()

                is CreateProductUiEffect.ShowError ->
                    snackbarHostState.showSnackbar(effect.message)

                is CreateProductUiEffect.ShowSuccess ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            OutlinedTextField(
                value = state.code,
                onValueChange = {
                    viewModel.onEvent(
                        CreateProductUiEvent.CodeChanged(it)
                    )
                },
                label = { Text("Code") }
            )

            OutlinedTextField(
                value = state.description,
                onValueChange = {
                    viewModel.onEvent(
                        CreateProductUiEvent.DescriptionChanged(it)
                    )
                },
                label = { Text("Description") }
            )
            OutlinedTextField(
                value = state.category,
                onValueChange = {
                    viewModel.onEvent(
                        CreateProductUiEvent.CategoryChanged(it)
                    )
                },
                label = { Text("Category") }
            )

            OutlinedTextField(
                value = state.price,
                onValueChange = {
                    viewModel.onEvent(
                        CreateProductUiEvent.PriceChanged(it)
                    )
                },
                label = { Text("Precio") }
            )

            OutlinedTextField(
                value = state.stock,
                onValueChange = {
                    viewModel.onEvent(
                        CreateProductUiEvent.StockChanged(it)
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
                                CreateProductUiEvent.TaxableChanged(isChecked)
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
                        CreateProductUiEvent.SaveClicked
                    )
                    onNavigateBack()
                }
            ) {
                Text("Save")
            }
        }
    }
}
