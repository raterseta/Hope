package com.example.hope.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hope.R

@Composable
fun HomeChatPage(sizeWidth: androidx.compose.ui.unit.TextUnit, screenWidth: androidx.compose.ui.unit.Dp, poppins_bold: FontFamily, poppins_regular: FontFamily) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (
            modifier = Modifier
                .background(Color(0xFF8EACCD))
                .fillMaxWidth()
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
                    text = "Akun saya",
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
            Box(
                modifier = Modifier
                    .padding(bottom = screenWidth * 0.05f)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 20.dp, // Tingkat bayangan
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp), // Hanya sudut bawah
                        clip = false
                    )
            ) { Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White) // Pastikan latar belakang putih
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = screenWidth * 0.05f, bottom = screenWidth * 0.05f)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profileee),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(screenWidth * 0.17f)
                                .offset(x = screenWidth * 0.05f)
                        )
                        Text(
                            text = "Nalo Nama",
                            fontSize = sizeWidth * 0.05f,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppins_bold,
                            modifier = Modifier
                                .padding(start = screenWidth * 0.07f)
                                .offset(y = screenWidth * 0.03f)
                        )


                    }
                }
            }

            Column (modifier = Modifier){
                Column (modifier = Modifier
                    .padding(start = screenWidth*0.05f, end = screenWidth*0.05f)
                ){
                    /*
                    * Buat menjadi logika, untuk psikolog yang aktif maka dia akan ditaruh diatas dan berwarna 0xFFDEE5D4
                    * Jika yang tidak menyala, maka akan ditaruh dibawahnya
                    * Buat Statik dulu untuk Akun User, Buat Ada 5
                    * */
                    //Psikolog lain yang sedang melakukan chatting dengan client
                    Column (modifier = Modifier
                        .background(Color(0xFFDEE5D4), shape = RoundedCornerShape(screenWidth*0.03f)) // Menambahkan sudut tumpul
                        .fillMaxWidth()
                    ){
                        Row (verticalAlignment = Alignment.CenterVertically,modifier = Modifier
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
                                text = "Psikolog yang aktif",
                                fontSize = sizeWidth*0.045f,
                                fontWeight = FontWeight.Bold,
                                fontFamily = poppins_bold,
                                modifier = Modifier
                                    .padding(start= screenWidth*0.07f, end = screenWidth*0.08f)
//                                    .offset(y = screenWidth*0.03f)

                            )
                            Box(
                                modifier = Modifier
                                    .size(screenWidth*0.05f) // Menentukan ukuran lingkaran
                                    .clip(CircleShape) // Membuat bentuk lingkaran
                                    .background(Color.Green) // Memberikan warna hijau
                                    .padding(top = screenWidth*0.05f)
                            )
                        }
                    }

                    //Psikolog lain tidak aktif chatting dengan client
                    Column (modifier = Modifier)
                    {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                            .padding(top = screenWidth*0.05f, bottom = screenWidth*0.05f),

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
                                text = "Psikolog yang tidak aktif",
                                fontSize = sizeWidth*0.045f,
                                fontWeight = FontWeight.Bold,
                                fontFamily = poppins_bold,
                                modifier = Modifier
                                    .padding(start= screenWidth*0.07f)
//                                    .offset(y = screenWidth*0.03f)

                            )
                        }
                    }
                }
            }
        }


    }
}
