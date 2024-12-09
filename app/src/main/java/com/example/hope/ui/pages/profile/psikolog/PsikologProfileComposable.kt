package com.example.hope.ui.pages.profile.psikolog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hope.ui.pages.register.PsikologData
import com.example.hope.ui.pages.register.UserData

@Composable
fun ProfilePsikologComposable(
    modifier: Modifier = Modifier,
    userData: UserData,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val psikologData = userData.psikologData ?: PsikologData()
    var startTime by remember { mutableStateOf(psikologData.startTime) }
    var endTime by remember { mutableStateOf(psikologData.endTime) }

    val navController = rememberNavController()

    NavHost(navController= navController, startDestination = "profile"){
        composable("profile"){
            ProfilePsikologPage(
                onBackClick = onBackClick,
                onEditClick = { navController.navigate("edit") },
                onLogoutClick = onLogoutClick
            )
        }
        composable("edit"){
            EditProfilePsikologPage(
                onBackClick = { navController.navigate("profile") }
            )
        }
    }
}