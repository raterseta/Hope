package com.example.hope.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.hope.Greeting
import com.example.hope.R
import com.example.hope.ui.composables.topNav.TopNavChatComposable
import com.example.hope.ui.theme.HopeTheme

@Composable
fun HomeChatPage(modifier: Modifier = Modifier) {
    val poppins_regular = FontFamily(Font(R.font.poppins_regular))
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // TopNav untuk halaman chat
            TopNavChatComposable()

            // Sisa konten halaman
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
            }
        }

        Column (modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
        ) {
            Column (modifier = Modifier){
                Column (modifier = Modifier
                    .padding(start = screenWidth*0.05f, top = screenWidth*0.03f, end = screenWidth*0.05f)
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
                                color = Color.Black,
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
                }
            }
        }


    }
}

@Preview
@Composable
fun mreview(){
    HomeChatPage()
}