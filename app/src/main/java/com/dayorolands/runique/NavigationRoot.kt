package com.dayorolands.runique

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dayorolands.auth.presentation.intro.IntroScreenRoot
import com.dayorolands.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        authGraph(navHostController = navController)
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
            Text(text = "Login")
        }
    }
}