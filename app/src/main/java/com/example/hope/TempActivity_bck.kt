package com.example.hope

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import com.example.hope.chat.HomeChatPage

data class BottomNavItemOldd(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun TempActivity_bck(
    sizeWidth: TextUnit,
    screenWidth: Dp,
    screenHeight: Dp,  // Added screenHeight for dynamic adjustments
    logoSize: Dp,
    poppinsBold: FontFamily,
    poppinsRegular: FontFamily,
    selectedPage: String
) {
    // Menentukan item bottom navigation
    val items = listOf(
        BottomNavItemOld(
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItemOld(
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add
        ),
        BottomNavItemOld(
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email
        ),
        BottomNavItemOld(
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite
        ),
    )

    // Menyimpan index item yang terpilih
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    // Menyesuaikan tinggi NavigationBar berdasarkan ukuran layar
    val navBarHeight = screenHeight * 0.08f  // Adjust height dynamically (8% of screen height)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF323C58),
                modifier = Modifier.height(navBarHeight)  // Apply dynamic height to the NavigationBar
            ) {
                // Menampilkan item navigasi berdasarkan daftar items
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                        },
                        icon = {
                            val iconSize = (screenWidth * 0.07f)
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val iconColor = when (index) {
                                    0 -> Color.White
                                    1 -> if (selectedPage == "ContentChatPage") Color(0xFF8EACCD) else Color.White
                                    2 -> if (selectedPage == "HomeChatPage") Color(0xFF8EACCD) else Color.White
                                    3 -> Color.White
                                    else -> Color.White
                                }

                                Icon(
                                    imageVector = when (index) {
                                        0 -> Icons.Filled.Home
                                        1 -> Icons.Filled.Add
                                        2 -> Icons.Filled.Email
                                        3 -> Icons.Filled.Favorite
                                        else -> Icons.Filled.Home
                                    },
                                    contentDescription = null,
                                    tint = iconColor,
                                    modifier = Modifier.size(iconSize)
                                )

                                // Menentukan warna teks berdasarkan status pemilihan ikon
                                Text(
                                    text = when (index) {
                                        0 -> "Home"
                                        1 -> "Add"
                                        2 -> "Email"
                                        3 -> "Favorite"
                                        else -> ""
                                    },
                                    fontSize = sizeWidth * 0.04f,
                                    fontFamily = poppinsRegular, // Menggunakan font Poppins
                                    fontStyle = FontStyle.Italic, // Mengatur teks menjadi miring
                                    color = iconColor  // Menyesuaikan warna teks dengan warna ikon
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,  // Warna ikon saat terpilih
                            unselectedIconColor = Color.White, // Warna ikon saat tidak terpilih
                            indicatorColor = Color.Transparent // Menghapus oval latar belakang
                        ),
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(0.dp)
                    )
                }
            }
        },
        content = { innerPadding ->
            when (selectedPage) {
//                "HomeChatPage" -> HomeChatPage(sizeWidth, screenWidth, poppinsBold, poppinsRegular)
//                "ContentChatPage" -> ContentChatPage(sizeWidth, screenWidth, logoSize, poppinsBold, poppinsRegular)
                else -> Text(
                    text = "Halaman tidak ditemukan",
                    fontSize = sizeWidth * 0.05f,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    )
}


@Preview
@Composable
private fun BottomNavPreview() {
    // TempActivity(sizeWidth = 16.sp, screenWidth = 360.dp, screenHeight = 640.dp, logoSize = 24.dp, poppinsBold = FontFamily.Default, poppinsRegular = FontFamily.Default, selectedPage = "HomeChatPage")
}
