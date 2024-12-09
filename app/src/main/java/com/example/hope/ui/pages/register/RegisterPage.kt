package com.example.hope.ui.pages.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hope.R


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hope.ui.composables.template.CustomTextFieldWhite


@Composable
fun RegisterPage(
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmedPassword by viewModel.confirmedPassword.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()
    val confirmedPasswordVisible by viewModel.confirmedPasswordVisible.collectAsState()

    Scaffold(
        containerColor = Color(80, 100, 147)
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
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Register",
                    color = Color.Black,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Email", color = Color.Black, fontSize = 16.sp)
                CustomTextFieldWhite(
                    value = email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    placeholder = "JohnDoe@gmail.com"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Password", color = Color.Black, fontSize = 16.sp)
                CustomTextFieldWhite(
                    value = password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = "********",
                    isPasswordField = true,
                    isPasswordVisible = passwordVisible,
                    togglePasswordVisibility = { viewModel.togglePasswordVisibility() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Confirm Password", color = Color.Black, fontSize = 16.sp)
                CustomTextFieldWhite(
                    value = confirmedPassword,
                    onValueChange = { viewModel.onConfirmedPasswordChange(it) },
                    placeholder = "********",
                    isPasswordField = true,
                    isPasswordVisible = confirmedPasswordVisible,
                    togglePasswordVisibility = { viewModel.toggleConfirmedPasswordVisibility() }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { onRegisterClick() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(42.dp)
                        .width(320.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.4f)
                    )
                ) {
                    Text(text = "Register", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onGoogleSignInClick,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(42.dp)
                        .width(320.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.4f)),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = "Google Logo",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sign Up with Google",
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Sudah punya akun?", color = Color.Black)
                    TextButton(onClick = onLoginClick) {
                        Text(text = "Login", color = Color.Black)
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RegisterPagePreview() {
    RegisterPage(
        onBackClick = { println("Back clicked") },
        onRegisterClick = { },
        onGoogleSignInClick = { println("Google Sign-In clicked") },
        onLoginClick = { println("Login clicked") }
    )
}
