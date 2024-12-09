package com.example.hope.ui.pages.profile.psikolog

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hope.ui.pages.register.PsikologData
import com.example.hope.ui.pages.register.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PsikologProfileViewModel : ViewModel() {
    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData

    private val _psikologData = MutableStateFlow(PsikologData())
    val psikologData: StateFlow<PsikologData> = _psikologData

    // Fungsi untuk mengubah username
    fun updateUsername(newUsername: String) {
        _userData.update { currentState ->
            currentState.copy(username = newUsername)
        }
    }

    // Fungsi untuk mengubah email
    fun updateEmail(newEmail: String) {
        _userData.update { currentState ->
            currentState.copy(email = newEmail)
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

    fun updateStartTime(it: String) {
        _psikologData.update { currentState ->
            currentState.copy(startTime = it )
        }
    }

    fun updateEndTime(it: String) {

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
            val updatedUserData = _userData.value.copy(psikologData = _psikologData.value)

            // Simpan ke Firebase
            FirebaseDatabase.getInstance().getReference("users")
                .child(user.uid)
                .setValue(updatedUserData)
                .addOnSuccessListener {
                    // Berhasil menyimpan profil
                    Log.d("PsikologProfileViewModel", "Profile saved successfully")
                }
                .addOnFailureListener { exception ->
                    // Gagal menyimpan profil
                    Log.e("PsikologProfileViewModel", "Failed to save profile", exception)
                }
        }
    }


}
