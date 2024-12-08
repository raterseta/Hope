package com.example.hope.ui.pages.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.hope.R

@Composable
fun RegisterProfilePage(
    viewModel: RegisterProfilePageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBackClick: () -> Unit,
    onConfirmClick: (Int?) -> Unit,
    onUploadGalleryClick: () -> Unit
) {
    val selectedAvatar by viewModel.selectedAvatar.collectAsState()

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                // Tombol Back
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Judul
                Text(
                    text = "Pilih avatar yang menggambarkan diri anda",
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Avatar Grid
                val avatarIds = listOf(
                    R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3,
                    R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6,
                    R.drawable.avatar7, R.drawable.avatar8, R.drawable.avatar9
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    for (rowIndex in 0..2) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            avatarIds.slice(rowIndex * 3 until (rowIndex + 1) * 3).forEach { avatarId ->
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (selectedAvatar == avatarId) Color.Gray.copy(alpha = 0.4f)
                                            else Color.Transparent
                                        )
                                        .padding(4.dp)
                                        .clickable { viewModel.selectAvatar(avatarId) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = avatarId),
                                        contentDescription = "Avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.size(72.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Upload Gallery
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Not interested?", color = Color.Black, fontSize = 16.sp)
                    TextButton(onClick = { onUploadGalleryClick() }) {
                        Text(text = "Upload Gallery", color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Tombol Confirm
                Button(
                    onClick = { onConfirmClick(viewModel.confirmSelection()) },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(48.dp)
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(83, 100, 147)
                    )
                ) {
                    Text(text = "Confirm", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterProfilePagePreview() {
    RegisterProfilePage(
        onBackClick = { println("Back clicked") },
        onConfirmClick = { avatarId -> println("Confirmed avatar: $avatarId") },
        onUploadGalleryClick = { println("Upload Gallery clicked") }
    )
}
