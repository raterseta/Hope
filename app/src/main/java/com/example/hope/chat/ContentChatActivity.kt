package com.example.hope.chat

import android.graphics.Paint
import android.text.TextPaint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


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
    val screeninputheight = calculateHeight(screenWidth = screenWidth, percent_float = 0.1f)

    var textState by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = screenWidth*0.01f)
            .background(Color(0xFFFFFFFF))
            .height(screeninputheight),
//        verticalAlignment = Alignment.CenterVertically
    ) {

        // Bagian input (kolom pertama)
        Row(
            modifier = Modifier
                .weight(0.01f)
//                .height(screeninputheight)
                .background(
                    color = Color(0xFFC3C3C3),  // Background putih
                    shape = RoundedCornerShape(calculateHeight(screenWidth = screenWidth, percent_float = 0.1f))  // Sudut tumpul
                )
                .border(
                    width = calculateHeight(screenWidth = screenWidth, percent_float = 0.0035f),  // Ketebalan border
                    color = Color.Black,  // Warna border abu-abu
                    shape = RoundedCornerShape(screenWidth*0.05f)  // Menyesuaikan dengan bentuk sudut tumpul
                )
                .fillMaxWidth()
                .padding(top = screenWidth*0.004f,bottom = screenWidth*0.004f)

        ) {
            // Tombol Emoji
            IconButton(
                onClick = { /* Aksi ketika tombol emoji ditekan */ },
                modifier = Modifier
                    .padding(start = screenWidth * 0.01f)
                    .height(calculateHeight(screenWidth = screenWidth, percent_float = 0.05f))
                    .offset(y=screenWidth*0.013f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.laugh),
                    contentDescription = "Emoji Button",
                    tint = Color.Black
                )
            }
            BasicTextField(
                value = textState,
                onValueChange = { textState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top=screenWidth*0.018f, bottom = screenWidth*0.018f, end = screenWidth*0.04f),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (textState.text.isEmpty()) {
                        Text("Ketik di sini...", color = Color.Gray) // Ubah teks placeholder
                    }
                    innerTextField()
                }
            )
        }

        // Bagian tombol (kolom kedua)
        Row(
            modifier = Modifier
                .padding(
                    top = screenWidth * 0.004f,
                    bottom = screenWidth * 0.015f,
                    start = screenWidth * 0.01f,
                    end = screenWidth * 0.01f,
                )
        ) {
            Button(
                onClick = {
                    if (textState.text.isNotBlank()) {
                        onSendMessage(textState.text)
                        textState = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .size(screenWidth * 0.12f)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8EACCD)
                ),
                contentPadding = PaddingValues(0.dp) // Menghapus padding dalam tombol
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.send), // Ikon "send"
                    contentDescription = "Send Button",
                    modifier = Modifier.size(screenWidth * 0.05f).offset(x = screenWidth*0.004f), // Ukuran ikon agar pas
                    tint = Color.Black // Warna ikon
                )
            }
        }


    }
}


@Composable
fun ContentChatPage(
    sizeWidth: TextUnit,
    screenWidth: Dp,
    logoSize: Dp,
    poppins_bold: FontFamily,
    poppins_regular: FontFamily
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Kolom utama untuk header dan body
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Header
            Column(
                modifier = Modifier
                    .background(Color(0xFF8EACCD))
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = screenWidth * 0.05f, bottom = screenWidth * 0.05f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profileee),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(screenWidth * 0.2f)
                            .offset(x = screenWidth * 0.04f)
                    )
                    Text(
                        text = "Nalo Nama",
                        fontSize = sizeWidth * 0.07f,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppins_bold,
                        modifier = Modifier
                            .padding(start = screenWidth * 0.09f)
                            .offset(y = screenWidth * 0.045f)
                    )
                }
            }

            // Body (Pesan dengan scrollable area)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Mengambil ruang yang tersedia
                    .verticalScroll(rememberScrollState()) // Membuat body scrollable
                    .background(Color(0xFFFFFFFF))
            ) {
                DynamicPaddingServerColumn(
                    text = "Pesan dari server: satusatusatu...",
                    sizeWidth = sizeWidth,
                    screenWidth = screenWidth,
                    poppins_bold = poppins_regular
                )
                DynamicPaddingClientColumn(
                    text = "Pesan dari client: satu",
                    sizeWidth = sizeWidth,
                    screenWidth = screenWidth,
                    poppins_bold = poppins_regular
                )
            }
        }

        // ChatInputField (Selalu di bagian bawah)
        Box (modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter) // Selalu di bagian bawah
        ){
            ChatInputField(
                screenWidth = screenWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = screenWidth*0.02f)
            ) { message ->
                // Callback untuk menangani pesan yang dikirim
                println("Pesan terkirim: $message")
            }

        }
    }
}


// Dynamic Padding Column
@Composable
fun DynamicPaddingServerColumn(
    text: String,
    sizeWidth: TextUnit,
    screenWidth: Dp,
    poppins_bold: FontFamily
) {
    val fontSize = sizeWidth * 0.05f
    val calculatedEndPadding = calculateDynamicEndPaddingServer(text, fontSize, screenWidth)

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
fun DynamicPaddingClientColumn(
    text: String,
    sizeWidth: TextUnit,
    screenWidth: Dp,
    poppins_bold: FontFamily
) {
    val fontSize = sizeWidth * 0.05f
    val calculatedStartPadding = calculateDynamicStartPaddingClient(text, fontSize, screenWidth)

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
fun calculateDynamicEndPaddingServer(
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
fun calculateDynamicStartPaddingClient(
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

@Composable
@Preview(showBackground = true)
fun ContentChatPagePreview() {
    val poppins_regular = FontFamily(Font(R.font.poppins_regular))
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val sizeWidth = configuration.screenWidthDp.sp
    val logoSize = screenWidth * 0.7f

    ContentChatPage(
        sizeWidth = sizeWidth, // Example font size
        screenWidth = screenWidth, // Example screen width
        logoSize = logoSize, // Example logo size
        poppins_bold = poppins_bold,
        poppins_regular = poppins_regular
    )
}
