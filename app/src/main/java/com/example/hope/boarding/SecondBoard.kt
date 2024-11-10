package com.example.hope.boarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hope.R

@Composable
fun SecondBoard(sizeWidth: androidx.compose.ui.unit.TextUnit, screenWidth: androidx.compose.ui.unit.Dp, logoSize: androidx.compose.ui.unit.Dp, poppins_bold: FontFamily, poppins_regular: FontFamily) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.second_board_st_line),
            fontSize = sizeWidth * 0.05f,
            fontWeight = FontWeight.Bold,
            fontFamily = poppins_bold,
            textAlign = TextAlign.Center,
            style = TextStyle(lineHeight = sizeWidth * 0.075f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = screenWidth * 0.01f, bottom = screenWidth * 0.015f)
        )
        Text(
            text = stringResource(R.string.second_board_nd_line),
            fontSize = sizeWidth*0.03f,
            fontWeight = FontWeight.W300,
            fontFamily = poppins_regular,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=screenWidth * 0.1f, end=screenWidth * 0.1f, bottom = screenWidth * 0.15f)
        )
        Image(
            painter = painterResource(id = R.drawable.second_board),
            contentDescription = "Logo",
            modifier = Modifier
                .size(logoSize)
                .offset(x = screenWidth * 0.02f)
        )
    }
}
