//file MainActivity.kt
package com.example.hope.ui.pages.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hope.boarding.BoardPage
import com.example.hope.chat.ContentChatPage
import com.example.hope.chat.HomeChatPage
import com.example.hope.logo.LogoPage
import com.example.hope.ui.pages.login.LoginPage
import com.example.hope.ui.pages.profile.ProfileComposable
import com.example.hope.ui.pages.register.RegisterComposable
import com.example.hope.ui.theme.HopeTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HopeTheme {
                AppNavHost()
            }
        }
    }
}
//change1


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    val startDestination = if (auth.currentUser != null) {
        "homePage" // Jika sudah login, arahkan ke homePage
    } else {
//        "registerPage" // Jika belum login, arahkan ke registerPage
        "logoPage"
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable("registerPage"){
            RegisterComposable(
                onBackClick = { navController.navigate("logoPage") },
                onCompleteRegistration = { navController.navigate("homePage") },
                onLoginClick = { navController.navigate("loginPage") }
            )
        }
        composable("loginPage") {
            LoginPage(
                onBackClick = { navController.navigate("logoPage") },
                onLoginClick = { navController.navigate("homePage") },
                onGoogleSignInClick = { TODO() },
                onRegisterClick = { navController.navigate("registerPage") },
                onForgotPasswordClick = { TODO() }
            )
        }
        composable("homePage") {
            HomePage(
                onProfileClick = { navController.navigate("profile") },
                navController = navController
            )
        }
        composable("profile") {
            ProfileComposable(
                navController = navController
            )
        }
        composable("boardPage") {
            BoardPage(
                onLoginClick = { navController.navigate("loginPage") },
                onRegisterClick = { navController.navigate("registerPage") }
            )
        }
        composable("logoPage") {
            LogoPage(
                navController = navController
            )
        }
<<<<<<< HEAD
        composable("homeChatPage") {
            HomeChatPage(navController = navController)
        }
        composable("contentChat") {
            ContentChatPage(navController = navController)
        }
        composable("homePage?tab={tab}") { backStackEntry ->
            val tab = backStackEntry.arguments?.getString("tab")
            HomePage(
                onProfileClick = { navController.navigate("profile") },
                navController = navController,
                initialTab = when (tab) {
                    "Chat" -> Screen.Chat
                    else -> Screen.Home
                }
            )
        }

=======
>>>>>>> 7d73c3b58825abd1bda380f33bf5f9d34a28711e
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HopeTheme {}
}