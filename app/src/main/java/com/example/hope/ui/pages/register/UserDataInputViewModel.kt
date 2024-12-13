package com.example.hope.ui.pages.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDataInputViewModel: ViewModel() {
    private val _selectedAvatar = MutableStateFlow<Int?>(null)
    val selectedAvatar: StateFlow<Int?> get() = _selectedAvatar

    fun selectAvatar(avatarId: Int) {
        _selectedAvatar.value = avatarId
    }

    fun confirmSelection(): Int? {
        return _selectedAvatar.value
    }

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()


    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updateBirthDate(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

    fun validateAndSubmitUserData(): Boolean {
        return username.value.isNotBlank() &&
                birthDate.value.isNotBlank() &&
                phoneNumber.value.isNotBlank()
    }

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    fun saveUserData(){
        val userID = auth.currentUser?.uid
        if (userID != null) {
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("users")

            val userData = UserData(
                userID = userID,
                email = auth.currentUser?.email ?: "", // Email dari Google
                username = _username.value,
                birthDate = _birthDate.value,
                phoneNumber = _phoneNumber.value,
                avatarID = _selectedAvatar.value
            )

            usersRef.child(userID).setValue(userData)
                .addOnSuccessListener {
                }
                .addOnFailureListener {

                }
        }
    }
}
