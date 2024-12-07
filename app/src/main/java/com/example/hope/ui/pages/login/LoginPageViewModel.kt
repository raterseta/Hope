package com.example.hope.ui.pages.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginPageViewModel : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordVisible = mutableStateOf(false)

    fun togglePasswordVisibility() {
        passwordVisible.value = !passwordVisible.value
    }
}
