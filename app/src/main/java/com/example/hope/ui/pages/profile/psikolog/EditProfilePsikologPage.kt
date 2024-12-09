package com.example.hope.ui.pages.profile.psikolog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
fun EditProfilePsikologPage(
    viewModel: PsikologProfileViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    // Collect the state from the ViewModel
    val userData by viewModel.userData.collectAsState(initial = UserData())

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }

            Text(
                text = "Edit Profile",
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Avatar
            if (userData.avatarID != null) {
                Image(
                    painter = painterResource(id = userData.avatarID!!),
                    contentDescription = "Profile Avatar",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { TODO() }) {
                Text(
                    text = "Change Profile Picture",
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Username
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
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tanggal Lahir
            Text(
                text = "Tanggal Lahir",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            DatePickerField(userData.birthDate) { selectedDate ->
                viewModel.updateBirthDate(selectedDate)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nomor Telepon
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
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Jadwal
            Text(
                text = "Jadwal",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                // Dropdown untuk waktu mulai
                TimeDropdownMenu(
                    label = "Start Time",
                    selectedTime = userData.psikologData?.startTime ?: "08:00",  // Default "08:00" jika kosong
                    onTimeSelected = { viewModel.updateStartTime(it) }
                )
                Spacer(modifier = Modifier.width(8.dp))

                // Dropdown untuk waktu selesai
                TimeDropdownMenu(
                    label = "End Time",
                    selectedTime = userData.psikologData?.endTime ?: "16:00",  // Default "16:00" jika kosong
                    onTimeSelected = { viewModel.updateEndTime(it) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

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


//@Preview(showBackground = true)
//@Composable
//fun EditProfilePsikologPagePreview() {
//    val previewViewModel = EditProfilePsikologPageViewModel().apply {
//        updateSelectedAvatar(R.drawable.avatar3)
//    }
//    EditProfilePsikologPage(
//        viewModel = previewViewModel,
//        onBackClick = { println("Back clicked") }
//    )
//}