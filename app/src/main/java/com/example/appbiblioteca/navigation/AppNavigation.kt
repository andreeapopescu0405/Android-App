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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appbiblioteca.ui.screens.BookDetailsScreen
import com.example.appbiblioteca.ui.screens.BookFormScreen
import com.example.appbiblioteca.ui.screens.HomeScreen
import com.example.appbiblioteca.ui.screens.LoginScreen
import com.example.appbiblioteca.ui.screens.MyRentalsScreen
import com.example.appbiblioteca.ui.screens.ProfileScreen
import com.example.appbiblioteca.ui.screens.RegisterScreen
import com.example.appbiblioteca.viewmodel.AuthViewModel
import com.example.appbiblioteca.viewmodel.LibraryViewModel

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object Details : Screen("details")
    data object Rentals : Screen("rentals")
    data object Profile : Screen("profile")
    data object AddBook : Screen("add_book")
    data object EditBook : Screen("edit_book")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = viewModel(),
    libraryViewModel: LibraryViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isLoggedIn = authViewModel.isLoggedIn.value

    val showBottomBar = isLoggedIn && (
            currentRoute == Screen.Home.route ||
                    currentRoute == Screen.Rentals.route ||
                    currentRoute == Screen.Profile.route
            )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (currentRoute) {
                            Screen.Login.route -> "Login"
                            Screen.Register.route -> "Register"
                            Screen.Home.route -> "Bibliotecă"
                            Screen.Details.route -> "Detalii carte"
                            Screen.Rentals.route -> "Închirierile mele"
                            Screen.Profile.route -> "Profil"
                            Screen.AddBook.route -> "Adaugă carte"
                            Screen.EditBook.route -> "Editează carte"
                            else -> "Bibliotecă"
                        }
                    )
                }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Home.route,
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Acasă"
                            )
                        },
                        label = { Text("Acasă") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.Rentals.route,
                        onClick = {
                            navController.navigate(Screen.Rentals.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Închirieri"
                            )
                        },
                        label = { Text("Închirieri") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.Profile.route,
                        onClick = {
                            navController.navigate(Screen.Profile.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Profil"
                            )
                        },
                        label = { Text("Profil") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    authViewModel = authViewModel,
                    onRegisterSuccess = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    authViewModel = authViewModel,
                    libraryViewModel = libraryViewModel,
                    onBookClick = {
                        navController.navigate(Screen.Details.route)
                    },
                    onAddBookClick = {
                        navController.navigate(Screen.AddBook.route)
                    }
                )
            }

            composable(Screen.Details.route) {
                BookDetailsScreen(
                    authViewModel = authViewModel,
                    libraryViewModel = libraryViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditBook = {
                        navController.navigate(Screen.EditBook.route)
                    }
                )
            }

            composable(Screen.AddBook.route) {
                BookFormScreen(
                    libraryViewModel = libraryViewModel,
                    isEditMode = false,
                    onSaveSuccess = {
                        navController.popBackStack()
                    },
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.EditBook.route) {
                BookFormScreen(
                    libraryViewModel = libraryViewModel,
                    isEditMode = true,
                    onSaveSuccess = {
                        navController.popBackStack()
                    },
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Rentals.route) {
                MyRentalsScreen(
                    authViewModel = authViewModel,
                    libraryViewModel = libraryViewModel
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    authViewModel = authViewModel,
                    libraryViewModel = libraryViewModel,
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}