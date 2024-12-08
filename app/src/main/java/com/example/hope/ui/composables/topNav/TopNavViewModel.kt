package com.example.hope.ui.composables.topNav

import androidx.lifecycle.ViewModel
import com.example.hope.ui.pages.register.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TopNavViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    private val _userProfile = MutableStateFlow<UserData?>(null)
    val userProfile: StateFlow<UserData?> get() = _userProfile

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _error.value = "User not logged in"
            return
        }

        _loading.value = true
        usersRef.child(userId).get()
            .addOnSuccessListener { snapshot ->
                _loading.value = false
                val user = snapshot.getValue(UserData::class.java)
                _userProfile.value = user
            }
            .addOnFailureListener { e ->
                _loading.value = false
                _error.value = "Failed to load user profile: ${e.message}"
            }
    }

    fun refreshUserProfile() {
        loadUserProfile()
    }
}
