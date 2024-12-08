package com.example.hope.boarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.google.accompanist.pager.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.rememberCoroutineScope
import com.example.hope.ui.theme.HopeTheme

class BoardActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Change the status bar color to yellow
//        window.statusBarColor = android.graphics.Color.BLUE  ->  saya ingin mengganti warna dengan 0xFF536493
        window.statusBarColor = android.graphics.Color.parseColor("#536493")
        setContent {
            HopeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF536493) // Set background color to #536493
                ) {
                    BoardPage()
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun BoardPage() {
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
        Color(0xFF536493), // Page 1 color
        Color(0xFFD2E0FB), // Page 2 color
        Color(0xFFDEE5D4)  // Page 3 color (you can adjust as needed)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageColors[pagerState.currentPage]), // Set background color based on page
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Swipeable pages
        HorizontalPager(
            count = 3, // Number of pages
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        ) { page ->
            when (page) {
                0 -> FirstBoard(sizeWidth, screenWidth, logoSize, poppins_bold, poppins_regular)
                1 -> SecondBoard(sizeWidth, screenWidth, logoSize, poppins_bold, poppins_regular)
                2 -> ThirdBoard(sizeWidth, screenWidth, logoSize, poppins_bold, poppins_regular)
            }
        }

        // Page indicator (dots)
        Row(
            modifier = Modifier.padding(vertical = screenWidth * 0.05f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                val color = if (pagerState.currentPage == index) Color.Black else Color.Gray
                Box(
                    modifier = Modifier
                        .size(screenWidth * 0.03f)
                        .background(color, shape = CircleShape)
                )

                // Add space between dots, but not after the last dot
                if (index < 2) {
                    Spacer(modifier = Modifier.width(screenWidth*0.03f))
                }
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
fun BoardPreview() {
    HopeTheme {
        BoardPage()
    }
}


