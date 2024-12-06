package com.example.hope.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hope.R
import com.example.hope.boarding.BoardPage
import com.example.hope.boarding.FirstBoard
import com.example.hope.boarding.SecondBoard
import com.example.hope.boarding.ThirdBoard
import com.example.hope.logo.LogoPage
import com.example.hope.ui.theme.HopeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

class AllChatActivity : ComponentActivity() {
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
                    AllChatPage()
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun AllChatPage() {
    val poppins_regular = FontFamily(Font(R.font.poppins_regular))
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp
    val logoSize = screenWidth * 0.7f

    // Define a pager state
    val pagerState = rememberPagerState()

    // Define colors for each page
    val pageColors = listOf(
        Color(0xFF8EACCD), // Page 1 color
        Color(0xFFD2E0FB), // Page 2 color
        Color(0xFFDEE5D4)  // Page 3 color (you can adjust as needed)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageColors[pagerState.currentPage]), // Set background color based on page
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //
        // Swipeable pages
        HorizontalPager(
            count = 3, // Number of pages
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        ) { page ->
            when (page) {
                0 -> HomeChatPage(sizeWidth, screenWidth, logoSize, poppins_bold, poppins_regular)
                1 -> ContentChatPage(sizeWidth, screenWidth, logoSize, poppins_bold, poppins_regular)
                2 -> ThirdBoard(sizeWidth, screenWidth, logoSize, poppins_bold, poppins_regular)
            }
        }


        // Persistent white box with buttons at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(topStart = screenWidth * 0.1f, topEnd = screenWidth * 0.1f))
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = screenWidth * 0.08f)
                ) {
                    Button(
                        onClick = { /* Handle login action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF536493)),
                        modifier = Modifier.weight(1f).padding(top = screenWidth * 0.05f)
                    ) {
                        Text(
                            text = "Masuk dengan Akun yang Sudah Ada",
                            color = Color.White,
                            fontFamily = poppins_regular,
                            fontSize = sizeWidth * 0.035f
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = screenWidth * 0.08f)
                ) {
                    Button(
                        onClick = { /* Handle register action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFF536493)),
                        modifier = Modifier.weight(1f).padding(start = 0.dp)
                    ) {
                        Text(
                            text = "Buat Akun Baru",
                            color = Color(0xFF536493),
                            fontFamily = poppins_regular,
                            fontSize = sizeWidth * 0.035f
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AllChatPreview() {
    HopeTheme {
        AllChatPage()
    }
}