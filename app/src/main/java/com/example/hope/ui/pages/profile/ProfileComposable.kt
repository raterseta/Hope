package com.example.hope.ui.pages.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.hope.ui.pages.profile.psikolog.ProfilePsikologComposable
import com.example.hope.ui.pages.profile.user.ProfileUserPage
import com.example.hope.ui.pages.profile.user.UserProfileComposable
import com.example.hope.ui.pages.register.Role
import com.example.hope.ui.pages.register.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun ProfileComposable(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()

    val currentUser = auth.currentUser
    val userData = remember { mutableStateOf(UserData()) }

    LaunchedEffect(currentUser) {
        loadUserData(currentUser?.uid, userData)
    }

    if (currentUser != null) {
        when (userData.value.role) {
            Role.Psikolog -> {
                ProfilePsikologComposable(
                    onBackClick = { navController.navigate("homePage") },
                    onLogoutClick = { navController.navigate("loginPage") {
                        // Clear backstack to prevent going back to profile page
                        popUpTo("homePage") { inclusive = true }
                    } }
                )
            }
            else -> {
                UserProfileComposable(
                    onBackClick = { navController.navigate("homePage") },
                    onLogoutClick = { navController.navigate("loginPage") {
                        // Clear backstack to prevent going back to profile page
                        popUpTo("homePage") { inclusive = true }
                    } }
                )
            }
        }
    } else {
        // Handle case when user is not logged in
        Text("User not logged in")
    }
}

// Fetch user data from Firebase or other sources
fun loadUserData(userId: String?, userData: MutableState<UserData>) {
    if (userId != null) {
        FirebaseDatabase.getInstance().getReference("users")
            .child(userId)
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.getValue(UserData::class.java)?.let { data ->
                    userData.value = data
                }
            }
            .addOnFailureListener {
                // Handle error
            }
    }
}