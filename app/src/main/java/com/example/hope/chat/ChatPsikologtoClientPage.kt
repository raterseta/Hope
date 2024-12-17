package com.example.hope.chat

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hope.R
import com.example.hope.ui.composables.topNav.TopNavChatAnotherUserComposable
import com.example.hope.ui.composables.topNav.TopNavChatComposable
import com.example.hope.ui.composables.topNav.TopNavViewModel
import com.example.hope.ui.pages.register.UserData
import kotlinx.coroutines.launch

@Composable
fun ChatPsikologtoClientPage(
    chatId: String,
    ChatPsikologtoCLientViewModel: ChatPsikologtoClientViewModel = viewModel(),
    navController: NavController,
    currentUserViewModel: TopNavViewModel = viewModel(),
    activeClient: UserData? // Pastikan data ini bisa null
) {
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var isEmojiPickerVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val currentUser by currentUserViewModel.userProfile.collectAsState()
    val isLoading by currentUserViewModel.loading.collectAsState()

    BackHandler {
        navController.navigate("homePage?tab=Chat") {
            popUpTo("homePage") { inclusive = true }
        }
    }

    // Periksa jika activeClient atau currentUser null
    if (currentUser == null || activeClient == null) {
        Text("Error: Missing user data", color = Color.Red)
        return
    }

    BackHandler {
        navController.navigate("homePage?tab=Chat") {
            popUpTo("homePage") { inclusive = true }
        }
    }
    // Mendengarkan pembaruan pesan
    DisposableEffect(chatId) {
        // Memulai listener untuk mendengarkan pembaruan pesan
        val listenerRegistration = ChatPsikologtoCLientViewModel.listenForMessages(chatId = chatId, psikologID = currentUser!!.userID) { updatedMessages ->
            messages = updatedMessages
        }

        // Clean up listener ketika composable keluar dari komposisi
        onDispose {
            listenerRegistration.remove() // Menghapus listener saat composable dibuang
        }
    }

    TopNavChatAnotherUserComposable(activeClient)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 98.dp)
            .background(color = backgroundColor)
    ) {
        // Daftar pesan
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false  // Pesan baru tampil di bawah
        ) {
            items(messages) { message ->
//                DynamicPaddingCurrentUserColumn(message = message) // Gunakan fungsi DynamicPaddingCurrentUserColumn untuk setiap pesan
//                if (message.clientId == "") {
//                    // Tampilkan pesan psikolog di kanan
//                    DynamicPaddingCurrentUserColumn(message)
//                } else {
//                    // Tampilkan pesan klien di kiri
//                    DynamicPaddingAnotherUserColumn(message)
//                }
                if (message.clientId == activeClient!!.userID)  {
                    // Tampilkan pesan psikolog di kanan
                    DynamicPaddingCurrentUserColumn(message)
                } else if (message.clientId == "") {
                    // Tampilkan pesan klien di kiri
                    DynamicPaddingAnotherUserColumn(message)
                } else {
                    Text("an")
                }
            }
        }

        if (isEmojiPickerVisible) {
            EmojiPicker(messageText, setMessageText = { messageText = it })
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Kolom input pesan
        Row (modifier = Modifier.padding(start = 10.dp,bottom = 10.dp), verticalAlignment = Alignment.CenterVertically){
            Row(
                modifier = Modifier
                    .weight(0.01f)
                    .background(
                        if (isDarkTheme) Color.White.copy(alpha = 0.9f) // tema terang
                        else Color.Gray.copy(alpha = 0.3f), // tema gelap
                        shape = RoundedCornerShape(46.dp)
                    )
                    .border(1.dp, Color.Black, RoundedCornerShape(46.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { isEmojiPickerVisible = !isEmojiPickerVisible }) {
                    Icon(
                        painter = painterResource(id = R.drawable.laugh),
                        contentDescription = "Emoji Picker",
                        modifier = Modifier.size(24.dp)
                    )
                }

                BasicTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier.weight(0.05f)
                )

//                Spacer(modifier = Modifier.width(8.dp))

            }
            Row (verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = {
                    // Ensure that the message is not empty before sending
                    if (messageText.isNotEmpty() && currentUser != null && activeClient != null) {
                        coroutineScope.launch {
                            try {
                                val success = ChatPsikologtoCLientViewModel.sendMessage(
                                    chatId = chatId,
                                    message = messageText,
                                    currentUserID = currentUser!!.userID,
                                    currentUserAvatarID = currentUser!!.avatarID ?: 0,
                                    currentUsername = currentUser!!.username,
                                    activeClientID = activeClient.userID)
                                if (success) {
                                    messageText = "" // Clear input after sending
                                } else {
                                    showToast(context, "Failed to send message")
                                }
                            } catch (e: Exception) {
                                showToast(context, "An error occurred: ${e.message}")
                            }
                        }
                    }
                },
                    enabled = !isLoading
                ) {
                    Icon(Icons.Filled.Send, contentDescription = "Send",
                        tint = if (isDarkTheme) Color.White else Color.Black)
                }
            }
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


//@Composable
//fun ChatPsikologtoClientPage(
//    chatId: String,
//    chatViewModel: ChatPsikologtoClientViewModel = viewModel(),
//    navController: NavController,
//    currentUserViewModel: TopNavViewModel = viewModel(),
//    activeClient: UserData?
//) {
//    var messageText by remember { mutableStateOf("") }
//    var messages by remember { mutableStateOf(listOf<Message>()) }
//    val coroutineScope = rememberCoroutineScope()
//    val currentUser by currentUserViewModel.userProfile.collectAsState()
//
//    // Periksa jika activeClient atau currentUser null
//    if (currentUser == null || activeClient == null) {
//        Text("Error: Missing user data", color = Color.Red)
//        return
//    }
//
//    // Mendengarkan pembaruan pesan
//    DisposableEffect(chatId) {
//        val listenerRegistration = chatViewModel.listenForMessages(
//            chatId = chatId,
//            psikologID = currentUser!!.userID
//        ) { updatedMessages ->
//            messages = updatedMessages
//        }
//        onDispose {
//            listenerRegistration.remove()
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        LazyColumn(modifier = Modifier.weight(1f), reverseLayout = false) {
//            items(messages) { message ->
//                println("Message ClientID: \${message.clientId}")
//                println(message)
//                if (message.clientId == "") {
//                    // Tampilkan pesan psikolog di kanan
//                    DynamicPaddingCurrentUserColumn(message)
//                } else {
//                    // Tampilkan pesan klien di kiri
//                    DynamicPaddingAnotherUserColumn(message)
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            BasicTextField(
//                value = messageText,
//                onValueChange = { messageText = it },
//                textStyle = TextStyle(color = Color.Black),
//                modifier = Modifier.weight(1f).padding(16.dp).border(1.dp, Color.Gray)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            IconButton(onClick = {
//                if (messageText.isNotEmpty()) {
//                    coroutineScope.launch {
//                        val success = chatViewModel.sendMessage(
//                            chatId = chatId,
//                            message = messageText,
//                            currentUserID = currentUser!!.userID,
//                            currentUserAvatarID = currentUser!!.avatarID ?: 0,
//                            currentUsername = currentUser!!.username,
//                            activeClientID = activeClient.userID
//                        )
//                        if (success) messageText = ""
//                    }
//                }
//            }) {
//                Icon(painter = painterResource(id = R.drawable.send), contentDescription = "Send Message")
//            }
//        }
//    }
//}
//
//
//
//
//// Fungsi untuk menampilkan Toast
