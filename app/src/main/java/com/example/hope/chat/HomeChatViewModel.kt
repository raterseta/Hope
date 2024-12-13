package com.example.hope.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeChatViewModel : ViewModel() {

    private val _userRole = MutableLiveData<String>()
    val userRole: LiveData<String> get() = _userRole

    init {
        fetchUserRole()
    }

    private fun fetchUserRole() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .get()
                .addOnSuccessListener { snapshot ->
                    val role = snapshot.child("role").value as? String
                    _userRole.value = role ?: "Regular" // Default ke "Regular" jika role tidak ditemukan
                }
                .addOnFailureListener {
                    _userRole.value = "Regular" // Default jika terjadi error
                }
        } else {
            _userRole.value = "Regular" // Default jika tidak ada user yang login
        }
    }
}
