package com.example.hope.ui.pages.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterPageViewModel : ViewModel() {
    // State untuk email, password, dan konfirmasi password
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _confirmedPassword = MutableStateFlow("")
    val confirmedPassword: StateFlow<String> get() = _confirmedPassword

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> get() = _passwordVisible

    private val _confirmedPasswordVisible = MutableStateFlow(false)
    val confirmedPasswordVisible: StateFlow<Boolean> get() = _confirmedPasswordVisible

    // Fungsi untuk memperbarui state
    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmedPasswordChange(newConfirmedPassword: String) {
        _confirmedPassword.value = newConfirmedPassword
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun toggleConfirmedPasswordVisibility() {
        _confirmedPasswordVisible.value = !_confirmedPasswordVisible.value
    }
}
