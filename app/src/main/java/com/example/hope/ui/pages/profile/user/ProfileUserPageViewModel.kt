package com.example.hope.ui.pages.profile.user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hope.ui.pages.register.AuthState
import com.google.firebase.auth.FirebaseAuth

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

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    fun signout(onLogoutComplete: () -> Unit) {
        try {
            auth.signOut()
            onLogoutComplete() // Callback untuk melakukan navigasi setelah logout
        } catch (e: Exception) {
            // Handle error jika ada
            Log.e("ProfilePage", "Logout error: ${e.message}")
        }
    }
}
