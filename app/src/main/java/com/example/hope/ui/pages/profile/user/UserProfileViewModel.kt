package com.example.hope.ui.pages.profile.user

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hope.ui.pages.register.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UserProfileViewModel: ViewModel() {
    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData

    // Fungsi untuk mengubah username
    fun updateUsername(newUsername: String) {
        _userData.update { currentState ->
            currentState.copy(username = newUsername)
        }
    }

    // Fungsi untuk mengubah tanggal lahir
    fun updateBirthDate(newDate: String) {
        _userData.update { currentState ->
            currentState.copy(birthDate = newDate)
        }
    }

    // Fungsi untuk mengubah nomor telepon
    fun updatePhoneNumber(newPhoneNumber: String) {
        _userData.update { currentState ->
            currentState.copy(phoneNumber = newPhoneNumber)
        }
    }

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            FirebaseDatabase.getInstance().getReference("users")
                .child(user.uid)
                .get()
                .addOnSuccessListener { snapshot ->
                    snapshot.getValue(UserData::class.java)?.let { data ->
                        _userData.value = data
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("PsikologProfileViewModel", "Failed to load profile", exception)
                }
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun saveProfile() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            // Gabungkan data userData dan psikologData
            val updatedUserData = _userData.value

            // Simpan ke Firebase
            FirebaseDatabase.getInstance().getReference("users")
                .child(user.uid)
                .setValue(updatedUserData)
                .addOnSuccessListener {
                    // Berhasil menyimpan profil
                    Log.d("UserProfile", "Profile saved successfully")
                }
                .addOnFailureListener { exception ->
                    // Gagal menyimpan profil
                    Log.e("UserProfile", "Failed to save profile", exception)
                }
        }
    }
}