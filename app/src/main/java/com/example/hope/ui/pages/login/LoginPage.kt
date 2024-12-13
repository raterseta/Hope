package com.example.hope.ui.pages.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hope.R
import com.example.hope.ui.composables.template.CustomTextFieldWhite
import com.example.hope.ui.pages.register.AuthState


@Composable
fun LoginPage(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    viewModel: LoginPageViewModel = viewModel()
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val passwordVisible = viewModel.passwordVisible.value
    val authState by viewModel.authState.collectAsState()

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            viewModel.handleGoogleSignInResult(data, context, onUserNotFound = {
                Toast.makeText(context, "User not found. Please register.", Toast.LENGTH_SHORT).show()
            })
        }
    }


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
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Senang melihat Anda kembali.",
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Email", color = Color.Black, fontSize = 16.sp)
                CustomTextFieldWhite(
                    value = username,
                    onValueChange = { viewModel.onEmailChange(it) },
                    placeholder = "Email",
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Password", color = Color.Black, fontSize = 16.sp)
                CustomTextFieldWhite(
                    value = password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = "******",
                    isPasswordField = true,
                    isPasswordVisible = passwordVisible,
                    togglePasswordVisibility = { viewModel.togglePasswordVisibility() }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { onForgotPasswordClick() }) {
                    Text(text = "Lupa Password?", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(42.dp)
                            .width(320.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.4f))
                    ) {
                        Text(text = "Login", color = Color.White)
                    }
                }

                LaunchedEffect(authState) {
                    when (authState) {
                        is AuthState.Authenticated -> {
                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                            onLoginClick()
                        }
                        is AuthState.Error -> {
                            Toast.makeText(
                                context,
                                "Login failed: ${(authState as AuthState.Error).message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> Unit
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                var forceChooseAccount by remember { mutableStateOf(false) }

                OutlinedButton(
                    onClick = {
                        viewModel.forceChooseAccount.value = true // Atur ulang sebelum mencoba login
                        viewModel.signInWithGoogle(
                            context = context,
                            launcher = launcher,
                            forceChooseAccount = forceChooseAccount,
                            onUserNotFound = {
                                Toast.makeText(context, "User not found. Please register.", Toast.LENGTH_SHORT).show()
                                // Reset pemilihan akun setelah gagal login
                                forceChooseAccount = true
                            }
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(42.dp)
                        .width(320.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.4f))
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
                            text = "Sign in with Google",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                LaunchedEffect(authState) {
                    when (authState) {
                        is AuthState.Authenticated -> {
                            forceChooseAccount = false // Reset pilihan akun setelah login sukses
                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                            onLoginClick()
                        }
                        is AuthState.Error -> {
                            Toast.makeText(
                                context,
                                "Login failed: ${(authState as AuthState.Error).message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> Unit
                    }
                }

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Belum punya akun?", color = Color.Black)
                    TextButton(onClick = { onRegisterClick() }) {
                        Text(text = "Register", color = Color.Black)
                    }
                }
            }
        }
    }
}

