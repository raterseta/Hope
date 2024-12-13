package com.example.hope.chat

import android.content.Context
import android.text.TextPaint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.example.hope.R
import kotlin.math.max
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.launch


import androidx.compose.ui.platform.LocalContext

import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.navigation.NavController
import com.example.hope.ui.composables.topNav.TopNavChatAnotherUserComposable
import com.example.hope.ui.composables.topNav.TopNavViewModel
import com.example.hope.ui.pages.register.UserData

@Composable
fun ChatClienttoPsikologPage(
    chatId: String,
    ChatClienttoPsikologViewModel: ChatClienttoPsikologViewModel = viewModel(),
    navController: NavController,
    currentUserViewModel: TopNavViewModel = viewModel(),
    activePsikolog: UserData?
) {
    // State untuk pesan yang diketik
    var messageText by remember { mutableStateOf("") }
    // State untuk pesan yang sudah dikirim
    var messages by remember { mutableStateOf(listOf<Message>()) }
    // Coroutine scope untuk melakukan pengiriman pesan
    val coroutineScope = rememberCoroutineScope()
    // Mendapatkan konteks untuk Toast
    val context = LocalContext.current
//    val currentUser by currentUserViewModel.userProfile.collectAsState()

    val currentUser by currentUserViewModel.userProfile.collectAsState()
    val isLoading by currentUserViewModel.loading.collectAsState()


    BackHandler {
        navController.navigate("homePage?tab=Chat") {
            popUpTo("homePage") { inclusive = true }
        }
    }

    // Mendengarkan pembaruan pesan
    DisposableEffect(chatId) {
        // Memulai listener untuk mendengarkan pembaruan pesan
        val listenerRegistration = ChatClienttoPsikologViewModel.listenForMessages(chatId, psikologID = activePsikolog!!.userID) { updatedMessages ->
            messages = updatedMessages
        }

        // Clean up listener ketika composable keluar dari komposisi
        onDispose {
            listenerRegistration.remove() // Menghapus listener saat composable dibuang
        }
    }

    TopNavChatAnotherUserComposable(activePsikolog)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, top = 30.dp)
    ) {

        Spacer(modifier = Modifier.padding(35.dp))
        // Daftar pesan
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false  // Pesan baru tampil di bawah
        ) {
            items(messages) { message ->
//                DynamicPaddingCurrentUserColumn(message = message) // Gunakan fungsi DynamicPaddingCurrentUserColumn untuk setiap pesan
                if (message.clientId != "") {
                    // Tampilkan pesan psikolog di kanan
                    DynamicPaddingCurrentUserColumn(message)
                } else {
                    // Tampilkan pesan klien di kiri
                    DynamicPaddingAnotherUserColumn(message)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Kolom input pesan
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kolom input pesan
            BasicTextField(
                value = messageText,
                onValueChange = { messageText = it },
                textStyle = TextStyle(color = Color.Black),
                enabled = !isLoading, // Disable kolom input saat loading
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (messageText.isNotEmpty() && currentUser != null && activePsikolog != null) {
                            coroutineScope.launch {
                                try {
                                    val success = ChatClienttoPsikologViewModel.sendMessage(
                                        chatId = chatId,
                                        message = messageText,
                                        currentUserID = currentUser!!.userID,
                                        currentUserAvatarID = currentUser!!.avatarID!!,
                                        currentUsername = currentUser!!.username,
                                        activePsikologID = activePsikolog!!.userID)
                                    if (success) {
                                        messageText = "" // Clear input after sending
                                    } else {
                                        showToast(context, "Failed to send message")
                                    }
                                } catch (e: Exception) {
                                    showToast(context, "An error occurred: ${e.message}")
                                }
                            }
                        } else {
                            showToast(context, "User data is still loading")
                        }
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Gray.copy(alpha = 0.1f), shape = MaterialTheme.shapes.medium)
                    .padding(12.dp)
            )

            // Tombol Kirim Pesan
            IconButton(
                onClick = {
                    if (messageText.isNotEmpty() && currentUser != null && activePsikolog != null) {
                        coroutineScope.launch {
                            try {
                                val success = ChatClienttoPsikologViewModel.sendMessage(
                                    chatId,
                                    messageText,
                                    currentUser!!.userID,
                                    activePsikologID = activePsikolog!!.userID,
                                    currentUserAvatarID = currentUser!!.avatarID!!,
                                    currentUsername = currentUser!!.username
                                    )
                                if (success) {
                                    messageText = "" // Clear input after sending
                                } else {
                                    showToast(context, "Failed to send message")
                                }
                            } catch (e: Exception) {
                                showToast(context, "An error occurred: ${e.message}")
                            }
                        }
                    } else {
                        showToast(context, "User data is still loading")
                    }
                },
                enabled = !isLoading // Disable tombol kirim saat loading
            ) {
                Icon(imageVector = Icons.Filled.Send, contentDescription = "Send")
            }
        }

    }
}




// Fungsi untuk menampilkan Toast
private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun MessageItem(message: Message) {
    // Tampilkan pesan, bisa menggunakan gaya yang berbeda untuk pengirim
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = message.message,
            style = TextStyle(color = Color.Black),
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

data class Message(val clientId: String, val message: String, val timestamp: Long)


@Composable
fun DynamicPaddingAnotherUserColumn(message: Message) {
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp

//    val calculatedStartPadding = calculateDynamicStartPadding(message.message, fontSize, screenWidth)
    val fontSize = sizeWidth * 0.05f
    val calculatedEndPadding = calculateDynamicEndPadding(message.message, fontSize, screenWidth)

    Column(
        modifier = Modifier
            .padding(top = screenWidth * 0.05f, end = calculatedEndPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFDEE5D4))
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        top = screenWidth * 0.005f,
                        bottom = screenWidth * 0.05f
                    )
            ) {
                Text(
                    text = message.message,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins_bold,
                    modifier = Modifier
                        .padding(start = screenWidth * 0.05f, end = screenWidth * 0.05f)
                        .offset(y = screenWidth * 0.03f),
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}

@Composable
fun DynamicPaddingCurrentUserColumn(message: Message) {
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp

    val fontSize = sizeWidth * 0.05f
    val calculatedStartPadding = calculateDynamicStartPadding(message.message, fontSize, screenWidth)

    Column(
        modifier = Modifier
            .padding(top = screenWidth * 0.05f, start = calculatedStartPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF8EC1CD)) // Ubah warna sesuai dengan kebutuhan
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        top = screenWidth * 0.005f,
                        bottom = screenWidth * 0.05f
                    )
            ) {
                Text(
                    text = message.message,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins_bold,
                    modifier = Modifier
                        .padding(start = screenWidth * 0.05f, end = screenWidth * 0.05f)
                        .offset(y = screenWidth * 0.03f),
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}

// Function to calculate dynamic end padding using TextPaint
@Composable
fun calculateDynamicEndPadding(
    text: String,
    fontSize: TextUnit,
    screenWidth: Dp
): Dp {
    val density = LocalDensity.current
    val textPaint = remember {
        TextPaint().apply {
            textSize = with(density) { fontSize.toPx() }
        }
    }

    val textWidth = textPaint.measureText(text)
    val screenWidthPx = with(density) { screenWidth.toPx() }
    val minPadding = screenWidthPx * 0.05f // Minimal padding akhir

    // Hitung padding dinamis
    val dynamicPadding = max(minPadding, screenWidthPx - textWidth - screenWidthPx * 0.28f)

    return with(density) { dynamicPadding.toDp() }
}


@Composable
fun calculateDynamicStartPadding(
    text: String,
    fontSize: TextUnit,
    screenWidth: Dp
): Dp {
    val density = LocalDensity.current
    val textPaint = remember {
        TextPaint().apply {
            textSize = with(density) { fontSize.toPx() }
        }
    }

    val textWidth = textPaint.measureText(text)
    val screenWidthPx = with(density) { screenWidth.toPx() }
    val minPadding = screenWidthPx * 0.05f // Minimal padding awal

    // Hitung padding dinamis untuk sisi awal
    val dynamicPadding = max(minPadding, screenWidthPx - textWidth - screenWidthPx * 0.28f)

    return with(density) { dynamicPadding.toDp() }
}



