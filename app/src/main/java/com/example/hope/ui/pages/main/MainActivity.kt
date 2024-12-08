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
import com.example.hope.R
import com.example.hope.ui.pages.login.LoginPage
import com.example.hope.ui.pages.register.RegisterComposable
import com.example.hope.ui.pages.register.RegisterPage
import com.example.hope.ui.pages.register.RegisterProfilePage
import com.example.hope.ui.pages.register.RegisterUsernamePage
import com.example.hope.ui.pages.register.RegisterUsernamePageViewModel
import com.example.hope.ui.pages.user.ProfileUserPage
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
        "registerPage" // Jika belum login, arahkan ke registerPage
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable("registerPage"){
            RegisterComposable(
                onBackClick = { },
                onCompleteRegistration = { navController.navigate("homePage") },
                onLoginClick = { navController.navigate("loginPage") }
            )
        }
        composable("loginPage") {
            LoginPage(
                onBackClick = { TODO() },
                onLoginClick = { navController.navigate("homePage") },
                onGoogleSignInClick = { TODO() },
                onRegisterClick = { navController.navigate("registerPage") },
                onForgotPasswordClick = { TODO() }
            )
        }
        composable("homePage") {
            HomePage(
                onProfileClick = { navController.navigate("profile") }
            )
        }
        composable("profile") { 
            ProfileUserPage(
                selectedAvatarId = R.drawable.avatar3,
                onBackClick = { navController.navigate("homePage") },
                onEditClick = { TODO() },
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HopeTheme {}
}