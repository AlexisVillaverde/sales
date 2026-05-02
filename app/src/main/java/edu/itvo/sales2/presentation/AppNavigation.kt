package edu.itvo.sales2.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.itvo.sales2.presentation.customer.create.CreateCustomerScreen
import edu.itvo.sales2.presentation.customer.list.ListCustomerScreen
import edu.itvo.sales2.presentation.customer.update.UpdateCustomerScreen
import edu.itvo.sales2.presentation.product.create.CreateProductScreen
import edu.itvo.sales2.presentation.product.list.ListProductScreen
import edu.itvo.sales2.presentation.product.update.UpdateProductScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    /*Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("create_product")
                },
                containerColor = Color(0xFF00B8D4)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear producto")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "product_list",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("product_list") {
                ListProductScreen()
            }
            composable("create_product") {
                CreateProductScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("customer_list") {
                ListCustomerScreen()
            }

        }
    }*/
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar{
                NavigationBarItem(
                    selected = currentRoute == "product_list",
                    onClick = { navController.navigate("product_list") },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Create Product") },
                    label = { Text("Products") }
                )
                NavigationBarItem(
                    selected = currentRoute == "customer_list",
                    onClick = { navController.navigate("customer_list") },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Customers") },
                    label = { Text("Customers") }
                )
            }
        }
    ) {
            paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "product_list",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("product_list") {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate("create_product")
                            },
                            containerColor = MaterialTheme.colorScheme.primary
                        ){
                            Icon(Icons.Default.Add, contentDescription = "Create product")
                        }
                    }
                ) { innerPadding ->
                    ListProductScreen(Modifier.padding(innerPadding),
                        onNavigateToUpdate = { code ->
                            navController.navigate("update_product/$code")
                        }
                    )
                }
            }

            composable("create_product") {
                CreateProductScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("update_product/{productCode}") { backStackEntry ->
                val productCode = backStackEntry.arguments?.getString("productCode") ?: ""
                UpdateProductScreen(
                    productCode = productCode,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("customer_list"){
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate("create_customer")
                            },
                            containerColor = MaterialTheme.colorScheme.secondary
                        ){
                            Icon(Icons.Default.Add, contentDescription = "Create customer")

                        }
                    }
                ){
                        innerPadding ->
                    ListCustomerScreen(Modifier.padding(innerPadding),
                        onNavigateToUpdate = { id ->
                            navController.navigate("update_customer/$id")
                        }
                    )
                }
            }
            composable("create_customer") {
                CreateCustomerScreen (
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("update_customer/{customerId}") { backStackEntry ->
                val customerId = backStackEntry.arguments?.getString("customerId") ?: ""
                UpdateCustomerScreen(
                    customerId = customerId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}