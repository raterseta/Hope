package com.example.hope.ui.pages.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterUsernamePageViewModel : ViewModel() {
    // State untuk menyimpan informasi pengguna
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    // Fungsi untuk memperbarui username
    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    // Fungsi untuk memperbarui tanggal lahir
    fun updateBirthDate(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }

    // Fungsi untuk memperbarui nomor telepon
    fun updatePhoneNumber(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

    // Fungsi validasi input sebelum konfirmasi
    fun validateAndSubmitUserData(): Boolean {
        return username.value.isNotBlank() &&
                birthDate.value.isNotBlank() &&
                phoneNumber.value.isNotBlank()
    }

    // Fungsi untuk mereset data
    fun resetData() {
        _username.value = ""
        _birthDate.value = ""
        _phoneNumber.value = ""
    }
}