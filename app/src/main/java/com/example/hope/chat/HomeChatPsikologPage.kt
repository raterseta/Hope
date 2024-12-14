package com.example.hope.chat


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hope.Greeting
import com.example.hope.R
import com.example.hope.ui.composables.topNav.TopNavChatComposable
import com.example.hope.ui.composables.topNav.TopNavViewModel
import com.example.hope.ui.pages.register.UserData
import com.example.hope.ui.theme.HopeTheme
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.UUID


//@Composable
//fun HomeChatPsikologPage(modifier: Modifier = Modifier, navController: NavController) {
//    val poppins_regular = FontFamily(Font(R.font.poppins_regular))
//    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp.dp
//    val sizeWidth = configuration.screenWidthDp.sp
//
//    // Cek tema sistem
//    val isDarkTheme = isSystemInDarkTheme()
//    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
//
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            TopNavChatComposable()
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Black)
//            ) {
//            }
//        }
//
//        Column(modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxSize()
//            .background(backgroundColor) //Ganti backround ke hitam jika tema hp menggunakan dark theme
//        ) {
//            Column(modifier = Modifier) {
//                Column(modifier = Modifier
//                    .padding(start = screenWidth * 0.05f, top = screenWidth * 0.03f, end = screenWidth * 0.05f)
//                ) {
//
//                    Column(
//                        modifier = Modifier
//                            .background(Color(0xFFDEE5D4), shape = RoundedCornerShape(screenWidth * 0.03f)) // Menambahkan sudut tumpul
//                            .fillMaxWidth()
//                            .clickable { navController.navigate("contentChat") } // Navigasi ke contentChat
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier
//                                .padding(top = screenWidth * 0.05f, bottom = screenWidth * 0.05f)
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.profileee),
//                                contentDescription = "Logo",
//                                modifier = Modifier
//                                    .size(screenWidth * 0.17f)
//                                    .offset(x = screenWidth * 0.05f)
//                                    .padding()
//                            )
//                            Text(
//                                text = "Client yang aktif",
//                                fontSize = sizeWidth * 0.045f,
//                                color = Color.Black,
//                                fontWeight = FontWeight.Bold,
//                                fontFamily = poppins_bold,
//                                modifier = Modifier
//                                    .padding(start = screenWidth * 0.07f, end = screenWidth * 0.08f)
//                            )
//                            Box(
//                                modifier = Modifier
//                                    .size(screenWidth * 0.05f) // Menentukan ukuran lingkaran
//                                    .clip(CircleShape) // Membuat bentuk lingkaran
//                                    .background(Color.Green) // Memberikan warna hijau
//                                    .padding(top = screenWidth * 0.05f)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//
//
//    }
//}
//



//Duarr
//@Composable
//fun HomeChatPsikologPage(
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    getClientViewModel: GetClientRefreshViewModel = viewModel(),
//    currentUserViewModel: TopNavViewModel = viewModel(),
//) {
//    val currentUser by currentUserViewModel.userProfile.collectAsState()
//    val clientList by getClientViewModel.clientList.collectAsState() // State client list
//
//    val chatId = UUID.randomUUID().toString()
//
//    // Header atau navigasi
//    TopNavChatComposable()
//
//    Column(modifier = Modifier.fillMaxSize().padding(top = 110.dp, start = 16.dp, end = 16.dp)) {
//
//        // Daftar client
//        if (clientList.isNotEmpty()) {
//            clientList.forEach { client ->
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                        .background(Color(0xFFDEE5D4), shape = RoundedCornerShape(8.dp))
//                        .clickable {
//                            // Convert selected client to JSON string for navigation
//                            val gson = Gson()
//                            val activeClientJson = gson.toJson(client)
//
//                            // Navigate to ContentChatPage, passing both chatId and serialized activeClient
//                            navController.navigate("chatPsikologtoClientPage/$chatId/$activeClientJson")
//                        }
//                        .padding(16.dp)
//                ) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        // Render image using avatarID
//                        val avatarId = client.avatarID ?: 0
//                        val avatarResource = when {
//                            avatarId != 0 -> getAvatarResource(avatarId) // Menggunakan getAvatarResource untuk mencari resource
//                            else -> R.drawable.avatar5 // Default avatar jika avatarID null atau tidak valid
//                        }
//
//                        // Render Avatar Image
//                        Image(
//                            painter = painterResource(id = avatarResource),
//                            contentDescription = "Client Avatar",
//                            modifier = Modifier
//                                .size(48.dp)
//                                .clip(CircleShape)
//                        )
//
//                        Spacer(modifier = Modifier.width(16.dp))
//
//                        // Display Client's Name
//                        Column {
//                            Text(
//                                text = client.username ?: "Unknown Client", // Handle null username
//                                fontWeight = FontWeight.Bold,
//                                color = Color.Black
//                            )
//                            Text(text = "Avatar ID: ${client.avatarID}", color = Color.Black)
//                        }
//                    }
//                }
//            }
//        } else {
//            // Tampilkan pesan jika tidak ada client
//            Text(
//                text = "No clients found.",
//                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
//                color = Color.Gray
//            )
//        }
//
//        Box(
//            modifier = Modifier.fillMaxWidth().padding(bottom = 0.dp) // Mengisi seluruh layar
//        ) {
//            FloatingActionButton(
//                onClick = {
//                    // Panggil fungsi untuk mendapatkan semua data client dari Firestore
//                    currentUser?.let {
//                        getClientViewModel.getAllClients(psikologID = it.userID)
//                    }
//                },
//                modifier = Modifier
//                    .align(Alignment.BottomEnd) // Menempatkan FAB di pojok kanan bawah
//                    .padding(16.dp),
//                shape = CircleShape,
//                containerColor = Color(0xFFDEE5D4)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.reverse), // Tombol Refresh
//                    contentDescription = "Get Clients",
//                    tint = Color.Black
//                )
//            }
//        }
//    }
//}
//
//// Fungsi untuk mendapatkan resource avatar berdasarkan avatarId
//fun getAvatarResource(avatarId: Int): Int {
//    return when (avatarId) {
//        1 -> R.drawable.avatar1
//        2 -> R.drawable.avatar2
//        3 -> R.drawable.avatar3
//        else -> R.drawable.avatar4 // Default avatar jika tidak ada yang cocok
//    }
//}

@Composable
fun HomeChatPsikologPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    getClientViewModel: GetClientRefreshViewModel = viewModel(),
    currentUserViewModel: TopNavViewModel = viewModel(),
) {
    val isDarkTheme = isSystemInDarkTheme()
    val currentUser by currentUserViewModel.userProfile.collectAsState()
    val clientList by getClientViewModel.clientList.collectAsState() // State client list
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp


    val chatId = UUID.randomUUID().toString()

    // Header atau navigasi
//    TopNavChatComposable()

    // Log clientList untuk debugging
    println("Client list size: ${clientList.size}") // Debugging client list size

//    Column(modifier = Modifier.fillMaxSize().padding(top = 110.dp, start = 16.dp, end = 16.dp)) {
    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {

        TopNavChatComposable()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = screenWidth * 0.05f, vertical = screenWidth * 0.03f)
        ){
            // Hanya panggil getAllClients jika clientList kosong
            if (clientList.isEmpty()) {
                currentUser?.let {
                    getClientViewModel.getAllClients(psikologID = it.userID)
                }
            }

            // Daftar client
            if (clientList.isNotEmpty()) {
                clientList.forEach { client ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color(0xFFDEE5D4), shape = RoundedCornerShape(8.dp))
                            .clickable {
                                // Convert selected client to JSON string for navigation
                                val gson = Gson()
                                val activeClientJson = gson.toJson(client)

                                // Log untuk memeriksa data yang akan diteruskan
                                println("Navigating with activeClientJson: $activeClientJson")

                                // Navigate to ContentChatPage, passing both chatId and serialized activeClient
                                navController.navigate("chatPsikologtoClientPage/$chatId/$activeClientJson")
                            }
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Render image using avatarID
                            val avatarId = client.avatarID ?: 0 // Make sure to access avatarID directly
                            val avatarResource = when {
                                avatarId != 0 -> getAvatarResource(avatarId) // Load avatar from resources
                                else -> R.drawable.avatar1 // Default avatar if no avatarID
                            }

                            // Render Avatar Image
                            Image(
                                painter = painterResource(id = avatarId),
                                contentDescription = "Client Avatar",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Display Client's Name
                            Column {
                                Text(
                                    text = client.username ?: "Unknown Client", // Handle null username
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(text = "Avatar ID: ${client.avatarID}", color = Color.Black)
                            }
                        }
                    }
                }
            } else {
                // Tampilkan pesan jika tidak ada client
                Text(
                    text = "No clients found.",
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    color = Color.Gray
                )
            }
        }


        Box(
//            modifier = Modifier.fillMaxWidth().padding(bottom = 0.dp) // Mengisi seluruh layar
            modifier = Modifier.fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = {
                    // Panggil fungsi untuk mendapatkan semua data client dari Firestore
                    currentUser?.let {
                        getClientViewModel.getAllClients(psikologID = it.userID)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Menempatkan FAB di pojok kanan bawah
                    .padding(bottom = screenWidth * 0.25f, end = screenWidth * 0.05f),
                shape = CircleShape,
                containerColor = Color(0xFFDEE5D4)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.reverse), // Tombol Refresh
                    contentDescription = "Get Clients",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


// Fungsi untuk mendapatkan resource avatar berdasarkan avatarId
fun getAvatarResource(avatarId: Int): Int {
    return when (avatarId) {
        1 -> R.drawable.avatar1
        2 -> R.drawable.avatar2
        3 -> R.drawable.avatar3
        else -> R.drawable.avatar8 // Default avatar jika tidak ada yang cocok
    }
}




