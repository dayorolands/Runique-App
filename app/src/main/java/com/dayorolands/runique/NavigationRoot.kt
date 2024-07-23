package com.dayorolands.runique

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dayorolands.auth.presentation.intro.IntroScreenRoot
import com.dayorolands.auth.presentation.login.LoginScreenRoot
import com.dayorolands.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(isLoggedIn)"run" else "auth"
    ) {
        authGraph(navHostController = navController)
        runGraph(navHostController = navController)
    }
}

private fun NavGraphBuilder.authGraph(navHostController : NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro"){
            IntroScreenRoot(
                onSignUpClick = { navHostController.navigate("register")},
                onSignInClick = { navHostController.navigate("login")}
            )
        }
        composable("register"){
            RegisterScreenRoot(
                onSignInClick = { navHostController.navigate("login") {
                    popUpTo("register") {
                        inclusive = true
                        saveState = true
                    }
                    restoreState = true
                } },
                onSuccessfulRegistration = { navHostController.navigate("login") })
        }
        composable("login") {
            LoginScreenRoot(
                onLoginSuccess = {
                    navHostController.navigate("run") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navHostController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.runGraph(navHostController: NavHostController) {
    navigation(
        startDestination = "run_overview",
        route = "run"
    ) {
        composable("run_overview") {
            Text(text = "Run Overview")
        }
    }
}