package com.example.appbiblioteca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appbiblioteca.navigation.AppNavigation
import com.example.appbiblioteca.ui.theme.AppBibliotecaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBibliotecaTheme {
                AppNavigation()
            }
        }
    }
}