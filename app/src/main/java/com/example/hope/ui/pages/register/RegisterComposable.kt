package com.example.hope.ui.pages.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterComposable(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCompleteRegistration: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    var currentStep by remember { mutableStateOf(0) }

    val authState = viewModel.authState.observeAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            when (currentStep) {
                0 -> RegisterPage(
                    onBackClick = onBackClick,
                    onRegisterClick = {
                        if (viewModel.validatePasswordMatch()) {
                            currentStep = 1
                        }
                    },
                    onGoogleSignInClick = { TODO() },
                    onLoginClick = onLoginClick
                )
                1 -> RegisterProfilePage(
                    onBackClick = { currentStep = 0 },
                    onConfirmClick = { currentStep = 2 },
                    onUploadGalleryClick = { TODO() }
                )
                2 -> RegisterUsernamePage(
                    selectedAvatarId = viewModel.selectedAvatar.value,
                    onBackClick = { currentStep = 1 },
                    onConfirmClick = {
                        viewModel.printUserData() // Memanggil fungsi untuk mencetak data
                        viewModel.register()
                        onCompleteRegistration() // Menjalankan fungsi untuk menyelesaikan registrasi
                    }
                )
            }
        }
    }
}

