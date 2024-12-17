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
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
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

    DisposableEffect(chatId) {
        val listenerRegistration = ChatClienttoPsikologViewModel.listenForMessages(
            chatId, psikologID = activePsikolog!!.userID
        ) { updatedMessages ->
            messages = updatedMessages
        }

        onDispose {
            listenerRegistration.remove()
        }
    }

    TopNavChatAnotherUserComposable(activePsikolog)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 98.dp)
            .background(color = backgroundColor)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {
            items(messages) { message ->
                if (message.clientId.isNotEmpty()) {
                    DynamicPaddingCurrentUserColumn(message)
                } else {
                    DynamicPaddingAnotherUserColumn(message)
                }
            }
        }

        if (isEmojiPickerVisible) {
            EmojiPicker(messageText, setMessageText = { messageText = it })
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Duarr
        Row (modifier = Modifier.padding(start = 10.dp, bottom = 10.dp), verticalAlignment = Alignment.CenterVertically){
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


            }
            Row (verticalAlignment = Alignment.CenterVertically){
                IconButton(
                    onClick = {
                        if (messageText.isNotEmpty() && currentUser != null && activePsikolog != null) {
                            coroutineScope.launch {
                                try {
                                    val success = ChatClienttoPsikologViewModel.sendMessage(
                                        chatId = chatId,
                                        message = messageText,
                                        currentUserID = currentUser!!.userID,
                                        activePsikologID = activePsikolog!!.userID,
                                        currentUserAvatarID = currentUser!!.avatarID!!,
                                        currentUsername = currentUser!!.username
                                    )
                                    if (success) messageText = ""
                                } catch (e: Exception) {
                                    showToast(context, "Error: ${e.message}")
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




// Fungsi untuk menampilkan Toast
private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}



data class Message(val clientId: String, val message: String, val timestamp: Long)

@Composable
fun EmojiPicker(messageText: String, setMessageText: (String) -> Unit) {
    val emojis = listOf("🙂", "😄", "❤️", "👍", "🎉", "😎")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        emojis.forEach { emoji ->
            IconButton(
                onClick = { setMessageText(messageText + emoji) },
                modifier = Modifier.size(48.dp)
            ) {
                Text(text = emoji, fontSize = 28.sp)
            }
        }
    }
}



@Composable
fun DynamicPaddingAnotherUserColumn(message: Message) {
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp

    val fontSize = sizeWidth * 0.05f
    val calculatedEndPadding = calculateDynamicEndPadding(message.message, fontSize, screenWidth)

    Column (modifier = Modifier.padding(end= 16.dp)){
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
}

@Composable
fun DynamicPaddingCurrentUserColumn(message: Message) {
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp

    val fontSize = sizeWidth * 0.05f
    val calculatedStartPadding = calculateDynamicStartPadding(message.message, fontSize, screenWidth)

    Column (modifier = Modifier.padding(end = 0.dp)){
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



