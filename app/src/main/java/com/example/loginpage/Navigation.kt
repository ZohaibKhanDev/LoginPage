package com.example.loginpage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController=rememberNavController()
    NavHost(navController = navController, startDestination =Screen.SignUp.route ) {
        composable(Screen.SignUp.route){
            AuthScreen(navController)
        }
        composable(Screen.Login.route){
            LoginScreen(navController)
        }
    }
}

sealed class Screen(val route: String){
    object SignUp:Screen("SignUp")
    object Login:Screen("Login")
}