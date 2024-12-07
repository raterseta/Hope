package com.example.hope.ui.pages.psikolog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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

@Composable
fun ProfilePsikologPage(
    viewModel: ProfilePsikologPageViewModel = viewModel(),
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {
    // Collect the state from the ViewModel
    val state by viewModel.state.collectAsState()

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
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { onEditClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = Color.Black
                    )
                }
            }

            // Title
            Text(
                text = "Profile",
                color = Color.Black,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Avatar
            if (state.selectedAvatarId != null) {
                Image(
                    painter = painterResource(id = state.selectedAvatarId!!),
                    contentDescription = "Profile Avatar",
                    modifier = Modifier
                        .size(175.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Input
            Text(
                text = "Email",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            BasicTextField(
                value = state.email,
                onValueChange = { viewModel.updateEmail(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                decorationBox = { innerTextField ->
                    if (state.email.isEmpty()) {
                        Text(
                            text = "example@mail.com",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tanggal Lahir Input
            Text(
                text = "Tanggal Lahir",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            DatePickerField(state.birthDate) { selectedDate ->
                viewModel.updateBirthDate(selectedDate)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nomor Telepon Input
            Text(
                text = "Nomor Telepon",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            BasicTextField(
                value = state.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                decorationBox = { innerTextField ->
                    if (state.phoneNumber.isEmpty()) {
                        Text(
                            text = "+62",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Jadwal Input
            Text(
                text = "Jadwal",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicTextField(
                    value = state.startTime,
                    onValueChange = { viewModel.updateStartTime(it) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray.copy(alpha = 0.2f))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    decorationBox = { innerTextField ->
                        if (state.startTime.isEmpty()) {
                            Text(
                                text = "08:00",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = state.endTime,
                    onValueChange = { viewModel.updateEndTime(it) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray.copy(alpha = 0.2f))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    decorationBox = { innerTextField ->
                        if (state.endTime.isEmpty()) {
                            Text(
                                text = "10:00",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Logout Button
            Button(
                onClick = {
                    viewModel.logout()
                    onBackClick()
                },
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(83, 100, 147)
                )
            ) {
                Text(text = "Logout", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePsikologPagePreview() {
    val previewViewModel = ProfilePsikologPageViewModel().apply {
        updateSelectedAvatar(R.drawable.avatar3)
    }
    ProfilePsikologPage(
        viewModel = previewViewModel,
        onBackClick = { println("Back clicked") },
        onEditClick = { println("Edit clicked") }
    )
}