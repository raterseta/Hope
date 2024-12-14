package com.example.hope.ui.pages.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserDataInput(
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    viewModel: UserDataInputViewModel = viewModel()
) {
    var currentStep by remember { mutableStateOf(0) }
    val selectedAvatarId by viewModel.selectedAvatar.collectAsState()

    Scaffold(
        modifier =  Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(modifier.padding(paddingValues)) {
            when(currentStep){
                0 -> RegisterProfilePage(
                    onBackClick = { currentStep = 0 },
                    onConfirmClick = { currentStep = 2 },
                    onUploadGalleryClick = { TODO() }
                )
                2 -> RegisterUsernamePage(
                    selectedAvatarId = selectedAvatarId,
                    onBackClick = { currentStep = 1 },
                    onConfirmClick = {
                        viewModel.saveUserData() // panggil saveUserData()
                        onSaveClick() // Menjalankan fungsi untuk menyelesaikan registrasi
                    }
                )
            }
        }
    }
}