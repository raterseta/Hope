package com.example.hope.ui.pages.psikolog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditProfilePsikologState(
    val email: String = "",
    val birthDate: String = "",
    val phoneNumber: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val selectedAvatarId: Int? = null
)

class EditProfilePsikologPageViewModel : ViewModel() {
    private val _state = MutableStateFlow(EditProfilePsikologState())
    val state: StateFlow<EditProfilePsikologState> = _state.asStateFlow()

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

    fun saveProfile() {
        // Implement your save logic here
        // This could involve calling a repository to save the profile data
        viewModelScope.launch {
            // Example: profileRepository.saveProfile(state.value)
            println("Saving profile: ${state.value}")
        }
    }

    fun uploadAvatar() {
        // Implement avatar upload logic
        viewModelScope.launch {
            // Example: avatarRepository.uploadAvatar()
            println("Uploading avatar")
        }
    }
}