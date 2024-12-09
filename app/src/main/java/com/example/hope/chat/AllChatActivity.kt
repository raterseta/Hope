package com.example.hope.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
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
import com.example.hope.TempActivity
import com.example.hope.TempActivity_bck
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
fun AllChatPage(innerPadding: PaddingValues = PaddingValues(0.dp)) {
    val poppins_regular = FontFamily(Font(R.font.poppins_regular))
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
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
            .padding(innerPadding) // Apply the inner padding here
            .background(pageColors[pagerState.currentPage])
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        ) { page ->
            when (page) {
                0 -> TempActivity_bck(
                    screenHeight = screenHeight,
                    sizeWidth = sizeWidth,
                    screenWidth = screenWidth,
                    logoSize = logoSize,
                    poppinsBold = poppins_bold,
                    poppinsRegular = poppins_regular,
                    selectedPage = "ContentChatPage"
                )
                1 -> TempActivity_bck(
                    screenHeight = screenHeight,
                    sizeWidth = sizeWidth,
                    screenWidth = screenWidth,
                    logoSize = logoSize,
                    poppinsBold = poppins_bold,
                    poppinsRegular = poppins_regular,
                    selectedPage = "ContentChatPage"
                )
                // Tambahkan halaman lainnya jika diperlukan
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