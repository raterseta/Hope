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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hope.R
import com.example.hope.ui.theme.HopeTheme

class LogoActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Change the status bar color to yellow
        window.statusBarColor = android.graphics.Color.YELLOW

        setContent {
            HopeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF536493) // Set background color to #536493
                ) {
                    LogoPage()
                }
            }
        }
    }
}

@Composable
fun LogoPage() {
    // Get screen width and height
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // Set logo size to be a percentage of the screen width
    val logoSize = screenWidth * 0.5f // Adjust multiplier as needed (e.g., 0.5f for 50%)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF536493)), // Background color #536493
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center vertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_hope),
            contentDescription = "Google Logo",
            modifier = Modifier
                .size(logoSize) // Set dynamic size based on screen width
                .offset(x = 10.dp) // Shift 10.dp to the right
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogoPreview() {
    HopeTheme {
        LogoPage()
    }
}
