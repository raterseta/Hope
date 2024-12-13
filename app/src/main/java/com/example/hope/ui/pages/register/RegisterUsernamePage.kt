package com.example.hope.ui.pages.register

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
import com.example.hope.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hope.ui.composables.template.CustomTextFieldGrey
import com.example.hope.ui.composables.template.CustomTextFieldWhite
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegisterUsernamePage(
    selectedAvatarId: Int?,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    viewModel: UserDataInputViewModel = viewModel()

) {
    val username by viewModel.username.collectAsState()
    val birthDate by viewModel.birthDate.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()

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
            if (selectedAvatarId != null) {
                Image(
                    painter = painterResource(id = selectedAvatarId),
                    contentDescription = "Selected Avatar",
                    modifier = Modifier
                        .size(175.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Username
            Text(
                text = "Username",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            CustomTextFieldGrey(
                value = username,
                onValueChange = { viewModel.updateUsername(it) },
                placeholder = "contoh : FaiqSkibidi_"
            )

            // Input Tanggal Lahir
            Text(
                text = "Tanggal Lahir",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            CustomTextFieldGrey(
                value = birthDate,
                onValueChange = { viewModel.updateBirthDate(it) },
                placeholder = "DD/MM/YY"
            )

            // Input Nomor Telepon
            Text(
                text = "Nomor Telepon",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            CustomTextFieldGrey(
                value = phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                placeholder = "+62"
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol Confirm
            Button(
                onClick =  onConfirmClick ,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(83, 100, 147)
                )
            ) {
                Text(text = "Confirm", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterUsernamePagePreview() {
    RegisterUsernamePage(
        selectedAvatarId = R.drawable.avatar3,
        onBackClick = { println("Back clicked") },
        onConfirmClick = {     }
    )
}
