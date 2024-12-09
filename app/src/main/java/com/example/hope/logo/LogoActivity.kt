//FIle LogoActivity.kt
package com.example.hope.logo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hope.R
import com.example.hope.ui.theme.HopeTheme
import kotlinx.coroutines.delay
import androidx.activity.compose.BackHandler
import androidx.navigation.NavHostController

@Composable
fun LogoPage(navController: NavHostController) {
    // Get screen width
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // Get context
    val context = LocalContext.current

    // Set logo size to be a percentage of the screen width
    val logoSize = screenWidth * 0.5f

    // Navigate to BoardNavHost after 2 seconds
//    LaunchedEffect(Unit) {
//        delay(2000) // 2 seconds delay
//        (context as? ComponentActivity)?.setContent {
//            HopeTheme {
//                navController.navigate("boardPage")
//            }
//        }
//    }
    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds delay
        navController.navigate("boardPage") {
            popUpTo("logoPage") { inclusive = true } // Hapus logoPage dari stack navigasi
        }
    }


    // Prevent going back
    BackHandler {
        // Do nothing or minimize the app
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF536493)), // Background color #536493
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center vertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_hope),
            contentDescription = "Hope Logo",
            modifier = Modifier
                .size(logoSize) // Set dynamic size based on screen width
                .offset(x = 10.dp) // Shift 10.dp to the right
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LogoPreview() {
//    HopeTheme {
//        LogoPage(navController)
//    }
//}