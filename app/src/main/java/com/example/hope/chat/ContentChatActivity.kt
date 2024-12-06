package com.example.hope.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hope.R
import com.example.hope.ui.theme.HopeTheme

@Composable
fun ContentChatPage(sizeWidth: androidx.compose.ui.unit.TextUnit, screenWidth: androidx.compose.ui.unit.Dp, logoSize: androidx.compose.ui.unit.Dp, poppins_bold: FontFamily, poppins_regular: FontFamily) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
        ){
            Row (modifier = Modifier
                .padding(top = screenWidth*0.05f, bottom = screenWidth*0.05f)
            ){
                Image(
                    painter = painterResource(id = R.drawable.profileee),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(screenWidth*0.2f)
                        .offset(x = screenWidth * 0.04f)
                        .padding()
                )
                Text(
                    text = "Nalo Nama",
                    fontSize = sizeWidth*0.07f,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins_bold,
                    modifier = Modifier
                        .padding(start= screenWidth*0.09f)
                        .offset(y = screenWidth*0.045f)

                )
            }
        }
        Column (modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
        ) {
            Column (){
                Row (modifier = Modifier
                    .padding(top = screenWidth*0.05f, bottom = screenWidth*0.05f)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.profileee),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(screenWidth*0.17f)
                            .offset(x = screenWidth * 0.05f)
                            .padding()
                    )
                    Text(
                        text = "Nalo Nama",
                        fontSize = sizeWidth*0.05f,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppins_bold,
                        modifier = Modifier
                            .padding(start= screenWidth*0.07f)
                            .offset(y = screenWidth*0.03f)

                    )
                }
            }
            Column (){
                Column (modifier = Modifier
                    .padding(start = screenWidth*0.05f, end = screenWidth*0.05f)
                ){
                    Column (modifier = Modifier
                        .background(Color(0xFFDEE5D4))
                        .fillMaxWidth()
                    ){
                        Row (modifier = Modifier
                            .padding(top = screenWidth*0.05f, bottom = screenWidth*0.05f)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.profileee),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(screenWidth*0.17f)
                                    .offset(x = screenWidth * 0.05f)
                                    .padding()
                            )
                            Text(
                                text = "Nalo Nama",
                                fontSize = sizeWidth*0.05f,
                                fontWeight = FontWeight.Bold,
                                fontFamily = poppins_bold,
                                modifier = Modifier
                                    .padding(start= screenWidth*0.07f)
                                    .offset(y = screenWidth*0.03f)

                            )
                        }
                    }
                    Column () {
                        Row (modifier = Modifier
                            .padding(top = screenWidth*0.05f, bottom = screenWidth*0.05f)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.profileee),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(screenWidth*0.17f)
                                    .offset(x = screenWidth * 0.05f)
                                    .padding()
                            )
                            Text(
                                text = "Nalo Nama",
                                fontSize = sizeWidth*0.05f,
                                fontWeight = FontWeight.Bold,
                                fontFamily = poppins_bold,
                                modifier = Modifier
                                    .padding(start= screenWidth*0.07f)
                                    .offset(y = screenWidth*0.03f)

                            )
                        }
                    }
                }
            }
        }


    }
}
