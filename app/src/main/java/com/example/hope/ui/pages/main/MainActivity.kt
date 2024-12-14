//file MainActivity.kt
package com.example.hope.ui.pages.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hope.boarding.BoardPage
import com.example.hope.chat.ChatClienttoPsikologPage
import com.example.hope.chat.ChatClienttoPsikologViewModel
import com.example.hope.chat.ChatPsikologtoClientPage
import com.example.hope.chat.ContentChatPage
import com.example.hope.chat.HomeChatClientPage
import com.example.hope.chat.HomeChatPsikologPage
import com.example.hope.logo.LogoPage
import com.example.hope.ui.pages.login.LoginPage
import com.example.hope.ui.pages.profile.ProfileComposable
import com.example.hope.ui.pages.register.RegisterPage
import com.example.hope.ui.pages.register.UserData
import com.example.hope.ui.pages.register.UserDataInput
import com.example.hope.ui.theme.HopeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

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
            RegisterPage(
                onBackClick = { navController.navigate("boardPage") },
                onLoginClick = { navController.navigate("loginPage") },
                onSuccess = {navController.navigate("userDataInput")},
            )
        }
        composable("userDataInput"){
            UserDataInput(
                onSaveClick = { navController.navigate("homePage") },
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
        composable("homeChatClientPage") {
            HomeChatClientPage(navController = navController)
        }
        composable("homeChatPsikologPage") {
            HomeChatPsikologPage(navController = navController)
        }
//        composable("contentChat") { backStackEntry ->
//            ContentChatPage(navController = navController)
//        }
//        composable(
//            "contentChat/{activePsikolog}",
//            arguments = listOf(navArgument("activePsikolog") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val gson = Gson()
//            val psikologJson = backStackEntry.arguments?.getString("activePsikolog")
//            val activePsikolog = gson.fromJson(psikologJson, UserData::class.java)
//
//            ContentChatPage(navController = navController, activePsikolog = activePsikolog)
//        }
        composable("chatClienttoPsikologPage/{chatId}/{activePsikolog}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val activePsikologJson = backStackEntry.arguments?.getString("activePsikolog") ?: ""

            // Deserialize JSON menjadi UserData
            val gson = Gson()
            val activePsikolog = gson.fromJson(activePsikologJson, UserData::class.java)

            // Kirim ke ContentChatPage
            ChatClienttoPsikologPage(chatId = chatId, activePsikolog = activePsikolog, navController = navController)
        }

        composable("chatPsikologtoClientPage/{chatId}/{activeClient}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val activeClientJson = backStackEntry.arguments?.getString("activeClient") ?: ""

            // Log untuk memeriksa data yang diterima
            println("Received activeClientJson: $activeClientJson")

            // Deserialize JSON menjadi UserData
            val gson = Gson()
            val activeClient = try {
                gson.fromJson(activeClientJson, UserData::class.java)
            } catch (e: Exception) {
                println("Error deserializing activeClient: ${e.message}")
                null // Menghindari crash jika deserialisasi gagal
            }

            // Log jika deserialisasi gagal
            if (activeClient == null) {
                println("Failed to deserialize activeClient")
            }

            // Kirim ke ContentChatPage
            if (activeClient != null) {
                ChatPsikologtoClientPage(chatId = chatId, activeClient = activeClient, navController = navController)
            } else {
                // Tampilkan pesan error atau lakukan fallback
                println("Error: activeClient is null")
            }
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

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HopeTheme {}
}
