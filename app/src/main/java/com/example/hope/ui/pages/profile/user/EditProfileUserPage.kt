package com.example.hope.ui.pages.profile.user


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hope.R
import com.example.hope.ui.pages.register.UserData

@Composable
fun EditProfileUserPage(
    onBackClick: () -> Unit,
    viewModel: UserProfileViewModel = viewModel()
) {
    val userData by viewModel.userData.collectAsState(initial = UserData())

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tombol Back
            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            // Judul
            Text(
                text = "Profile",
                color = Color.Black,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gambar Avatar
            if (userData.avatarID!= null) {
                Image(
                    painter = painterResource(id = userData.avatarID!!),
                    contentDescription = "Selected Avatar",
                    modifier = Modifier
                        .size(175.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Upload Gallery
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Bored of the profile?", color = Color.Black, fontSize = 16.sp)
                TextButton(onClick = { TODO() }) {
                    Text(text = "Upload Gallery", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Email
            Text(
                text = "Username",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            BasicTextField(
                value = userData.username,
                onValueChange = { viewModel.updateUsername(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray.copy(alpha = 0.2f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        if (userData.username.isEmpty()) {
                            Text(
                                text = "Enter your username",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )

            // Input Tanggal Lahir
            Text(
                text = "Tanggal Lahir",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            DatePickerField(value = userData.birthDate, onValueChange = { viewModel.updateBirthDate(it) })

            // Input Nomor Telepon
            Text(
                text = "Nomor Telepon",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            BasicTextField(
                value = userData.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray.copy(alpha = 0.2f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        if (userData.phoneNumber.isEmpty()) {
                            Text(
                                text = "+62",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol Logout
            // Tombol Save
            Button(
                onClick = {
                    viewModel.saveProfile()
                    onBackClick()
                },
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(83, 100, 147)
                )
            ) {
                Text(text = "Save", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun DatePickerField(value: String, onValueChange: (String) -> Unit) {
    // Placeholder untuk TextField kalender
    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = "DD/MM/YYYY",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun EditProfileUserPagePreview() {
//    EditProfileUserPage(
//        selectedAvatarId = R.drawable.avatar3,
//        onBackClick = { println("Back clicked") },
//        onLogoutClick = { println("Logout clicked") },
//        onUploadGalleryClick = { println("Upload Gallery clicked") },
//        userData = userData
//    )
//}
