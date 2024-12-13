package com.example.hope.chat

import android.app.Application
import android.text.TextPaint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.example.hope.R
import kotlin.math.max

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.hope.ui.composables.topNav.TopNavChatAnotherUserComposable
import com.example.hope.ui.composables.topNav.TopNavChatComposable
import com.example.hope.ui.pages.register.UserData
import kotlinx.coroutines.launch


@Composable
fun ContentChatPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    activePsikolog: UserData,
    viewModel: ContentChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val poppinsRegular = FontFamily(Font(R.font.poppins_regular))
    val poppinsBold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val messages = viewModel.messages

    BackHandler {
        navController.navigate("homePage?tab=Chat") {
            popUpTo("homePage") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
//        TopNavChatComposable()

        TopNavChatAnotherUserComposable(activePsikolog)
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(bottom = screenWidth * 0.18f)
            ) {
                items(messages) { (isUser, message) ->
                    if (isUser) {
                        DynamicPaddingCurrentUserColumn(
                            text = message,
                            sizeWidth = sizeWidth,
                            screenWidth = screenWidth,
                            poppins_bold = poppinsRegular
                        )
                    } else {
                        DynamicPaddingAnotherUserColumn(
                            text = message,
                            sizeWidth = sizeWidth,
                            screenWidth = screenWidth,
                            poppins_bold = poppinsRegular
                        )
                    }
                }

                if (viewModel.isBotTyping) {
                    item {
                        Text(
                            text = "Bot is typing...",
                            modifier = Modifier.padding(8.dp),
                            color = Color.Gray
                        )
                    }
                }
            }

            ChatInputField(
                screenWidth = screenWidth,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(start = screenWidth * 0.02f, bottom = screenWidth * 0.03f)
            ) { message ->
                viewModel.sendMessage(message)
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(messages.size - 1)
                }
            }
        }
    }

    LaunchedEffect(messages.size) {
        coroutineScope.launch {
            lazyListState.animateScrollToItem(messages.size - 1)
        }
    }
}







@Composable
fun calculateHeight(screenWidth: Dp, percent_float: Float): Dp {
    val density = LocalDensity.current
    val calculatedHeight: Dp = with(density) {
        (screenWidth.toPx() * percent_float).toDp()
    }
    return calculatedHeight
}


@Composable
fun ChatInputField(
    screenWidth: Dp,
    modifier: Modifier = Modifier,
    onSendMessage: (String) -> Unit
) {

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White

    val buttonHeight = screenWidth * 0.12f // Tinggi tombol "Send"
    val maxLines = 3 // Maksimal 3 baris sebelum scroll aktif
    val textFieldMinHeight = buttonHeight
    val textFieldMaxHeight = buttonHeight * maxLines // Maksimal 3x tinggi tombol

    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val scrollState = rememberScrollState()

    // State untuk menampilkan dialog emoji
    var showEmojiDialog by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = screenWidth * 0.01f,
                bottom = screenWidth * 0.01f,
                end = screenWidth * 0.01f
            )
            .background(backgroundColor), //ganti background berdasar tema
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Kolom input teks
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = screenWidth * 0.01f)
                .background(
                    color = Color(0xFFC3C3C3),
                    shape = RoundedCornerShape(screenWidth * 0.1f)
                )
                .border(
                    width = calculateHeight(screenWidth, 0.0035f),
                    color = Color.Black,
                    shape = RoundedCornerShape(screenWidth * 0.1f)
                )
                .heightIn(min = textFieldMinHeight, max = textFieldMaxHeight)
                .clip(RoundedCornerShape(screenWidth * 0.1f)) // Membatasi area ke dalam bentuk kolom
                .padding(horizontal = screenWidth * 0.02f) // Padding internal
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tombol Emoji
                IconButton(
                    onClick = { showEmojiDialog = true }, // Tampilkan dialog emoji
                    modifier = Modifier
                        .padding(start = screenWidth * 0.02f)
                        .size(buttonHeight * 0.55f) // Ukuran tombol emoji
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.laugh),
                        contentDescription = "Emoji Button",
                        tint = Color.Black
                    )
                }

                // TextField dengan dukungan multi-baris dan tinggi dinamis
                BasicTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = screenWidth * 0.02f)
                        .verticalScroll(scrollState) // Aktifkan scroll saat melebihi maxLines
                        .clipToBounds(), // Membatasi konten agar tidak keluar dari batas
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 14.sp
                    ),
                    singleLine = false, // Multi-baris
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = screenWidth * 0.02f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (textState.text.isEmpty()) {
                                Text(
                                    text = "Ketik di sini...",
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        // Tombol Kirim
        Button(
            onClick = {
                if (textState.text.isNotBlank()) {
                    onSendMessage(textState.text)
                    textState = TextFieldValue("") // Reset input
                }
            },
            modifier = Modifier
                .width(screenWidth * 0.15f)  // Menambah lebar tombol
                .height(screenWidth * 0.12f) // Menjaga tinggi agar tetap bulat
                .clip(CircleShape)  // Menjaga bentuk bulat
                .padding(start = screenWidth * 0.01f, end = screenWidth * 0.01f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8EACCD)
            ),
            contentPadding = PaddingValues(0.dp) // Menghapus padding dalam tombol
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.send),
                contentDescription = "Send Button",
                modifier = Modifier.size(buttonHeight * 0.47f), // Ikon sesuai proporsi
                tint = Color.Black
            )
        }
    }

    // Dialog Emoji
    if (showEmojiDialog) {
        Dialog(onDismissRequest = { showEmojiDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    val emojiList = listOf(
                        "\uD83D\uDE0A", "\uD83D\uDE02", "\uD83D\uDE09", "\uD83D\uDE0D", "\uD83D\uDE01",
                        "\uD83D\uDE04", "\uD83D\uDE0F", "\uD83D\uDE14", "\uD83D\uDE22", "\uD83D\uDE2D"
                    )
                    items(emojiList) { emoji ->
                        Text(
                            text = emoji,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    textState = TextFieldValue(textState.text + emoji)
                                    showEmojiDialog = false
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DynamicPaddingAnotherUserColumn(
    text: String,
    sizeWidth: TextUnit,
    screenWidth: Dp,
    poppins_bold: FontFamily
) {
    val fontSize = sizeWidth * 0.05f
    val calculatedEndPadding = calculateDynamicEndPadding(text, fontSize, screenWidth)

    Column(
        modifier = Modifier
            .padding(top = screenWidth*0.05f, end = calculatedEndPadding)
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
                    text = text,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins_bold,
                    modifier = Modifier
                        .padding(start = screenWidth * 0.05f, end = screenWidth*0.05f)
                        .offset(y = screenWidth * 0.03f),
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}

@Composable
fun DynamicPaddingCurrentUserColumn(
    text: String,
    sizeWidth: TextUnit,
    screenWidth: Dp,
    poppins_bold: FontFamily
) {
    val fontSize = sizeWidth * 0.05f
    val calculatedStartPadding = calculateDynamicStartPadding(text, fontSize, screenWidth)

    Column(
        modifier = Modifier
            .padding(top = screenWidth*0.05f, start = calculatedStartPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF8EC1CD))
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
                    text = text,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins_bold,
                    modifier = Modifier
                        .padding(start = screenWidth * 0.05f, end = screenWidth*0.05f)
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
    val dynamicPadding = max(minPadding, screenWidthPx - textWidth - screenWidthPx * 0.2f)

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
    val dynamicPadding = max(minPadding, screenWidthPx - textWidth - screenWidthPx * 0.2f)

    return with(density) { dynamicPadding.toDp() }
}
