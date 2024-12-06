package com.example.hope.chat

import android.graphics.Paint
import android.text.TextPaint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

@Composable
fun HomeChatPage(
    sizeWidth: TextUnit,
    screenWidth: Dp,
    logoSize: Dp,
    poppins_bold: FontFamily,
    poppins_regular: FontFamily
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
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
        // Body
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
        ) {
            // Dynamic Padding Logic
            DynamicPaddingServerColumn(
//                text = "satu",
                text = "satusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatu",
                sizeWidth = sizeWidth,
                screenWidth = screenWidth,
                poppins_bold = poppins_regular
            )
            DynamicPaddingClientColumn(
                text = "satu",
//                text = "satusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatusatu",
                sizeWidth = sizeWidth,
                screenWidth = screenWidth,
                poppins_bold = poppins_regular
            )
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
