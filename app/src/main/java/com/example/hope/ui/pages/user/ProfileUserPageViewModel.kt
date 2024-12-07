package com.example.hope.ui.pages.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileUserPageViewModel : ViewModel() {
    var email = mutableStateOf("")
        private set

    var birthDate = mutableStateOf("")
        private set

    var phoneNumber = mutableStateOf("")
        private set

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updateBirthDate(newBirthDate: String) {
        birthDate.value = newBirthDate
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber.value = newPhoneNumber
    }
}
