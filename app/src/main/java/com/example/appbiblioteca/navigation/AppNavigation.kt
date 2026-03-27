package com.example.appbiblioteca.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appbiblioteca.ui.screens.BookDetailsScreen
import com.example.appbiblioteca.ui.screens.HomeScreen
import com.example.appbiblioteca.ui.screens.MyRentalsScreen
import com.example.appbiblioteca.ui.screens.ProfileScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Details : Screen("details")
    data object Rentals : Screen("rentals")
    data object Profile : Screen("profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bibliotecă") }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == Screen.Home.route,
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Acasă") },
                    label = { Text("Acasă") }
                )

                NavigationBarItem(
                    selected = currentRoute == Screen.Rentals.route,
                    onClick = {
                        navController.navigate(Screen.Rentals.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Închirieri") },
                    label = { Text("Închirieri") }
                )

                NavigationBarItem(
                    selected = currentRoute == Screen.Profile.route,
                    onClick = {
                        navController.navigate(Screen.Profile.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profil") },
                    label = { Text("Profil") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onBookClick = {
                        navController.navigate(Screen.Details.route)
                    }
                )
            }

            composable(Screen.Details.route) {
                BookDetailsScreen()
            }

            composable(Screen.Rentals.route) {
                MyRentalsScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}