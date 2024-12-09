package com.example.hope.ui.pages.user

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class EditProfileUserPageViewModel : ViewModel() {
    private val _username = mutableStateOf("")
    val username: State<String> get() = _username

    private val _birthDate = mutableStateOf("")
    val birthDate: State<String> get() = _birthDate

    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> get() = _phoneNumber

    fun onUsernameChange(newEmail: String) {
        _username.value = newEmail
    }

    fun onBirthDateChange(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }
}
