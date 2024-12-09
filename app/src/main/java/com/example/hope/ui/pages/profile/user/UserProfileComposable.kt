package com.example.hope.ui.pages.profile.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hope.ui.pages.register.UserData

@Composable
fun UserProfileComposable(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "profile"){
        composable("profile") {
            ProfileUserPage(
                onBackClick = onBackClick,
                onEditClick = { navController.navigate("edit") },
                onLogoutClick = onLogoutClick
            )
        }
        composable("edit") {
            EditProfileUserPage(
                onBackClick = { navController.navigate("profile") }
            )
        }
    }
}