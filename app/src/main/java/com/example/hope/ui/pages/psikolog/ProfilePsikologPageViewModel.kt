package com.example.hope.ui.pages.psikolog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfilePsikologState(
    val email: String = "",
    val birthDate: String = "",
    val phoneNumber: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val selectedAvatarId: Int? = null
)

class ProfilePsikologPageViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProfilePsikologState())
    val state: StateFlow<ProfilePsikologState> = _state.asStateFlow()

    // Simulate loading user profile data
    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        // In a real app, this would call a repository to fetch user data
        viewModelScope.launch {
            // Simulated data loading
            _state.update {
                it.copy(
                    email = "example@mail.com",
                    birthDate = "01/01/1990",
                    phoneNumber = "+62 812-3456-7890",
                    startTime = "08:00",
                    endTime = "10:00",
                    selectedAvatarId = null // Or load from preferences/repository
                )
            }
        }
    }

    fun logout() {
        // Implement logout logic
        viewModelScope.launch {
            // Clear user session
            // Call authentication repository to logout
            println("Logging out user")
            // Potentially navigate to login screen or clear app state
        }
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun updateBirthDate(birthDate: String) {
        _state.update { it.copy(birthDate = birthDate) }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _state.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updateStartTime(startTime: String) {
        _state.update { it.copy(startTime = startTime) }
    }

    fun updateEndTime(endTime: String) {
        _state.update { it.copy(endTime = endTime) }
    }

    fun updateSelectedAvatar(avatarId: Int) {
        _state.update { it.copy(selectedAvatarId = avatarId) }
    }
}