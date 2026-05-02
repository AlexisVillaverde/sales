package edu.itvo.sales2.presentation.customer.update


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdateCustomerScreen(
    viewModel: UpdateCustomerViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    customerId: String
) {

    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(customerId) {
        viewModel.onEvent(
            UpdateCustomerUiEvent.LoadCustomer(customerId)
        )
    }

        LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->

            when (effect) {

                UpdateCustomerUiEffect.NavigateBack ->
                    onNavigateBack()

                is UpdateCustomerUiEffect.ShowError ->
                    snackbarHostState.showSnackbar(effect.message)

                is UpdateCustomerUiEffect.ShowSuccess ->
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
                value = state.id,
                onValueChange = { },
                label = { Text("ID") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(
                        UpdateCustomerUiEvent.nameChanged(it)
                    )
                },
                label = { Text("Name") }
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(
                        UpdateCustomerUiEvent.emailChanged(it)
                    )
                },
                label = { Text("Email") }
            )
            Button(
                onClick = {
                    viewModel.onEvent(
                        UpdateCustomerUiEvent.SaveClicked
                    )
                    onNavigateBack()
                }
            ) {
                Text("Update Customer")
            }
        }
    }
}
