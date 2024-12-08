package com.example.hope.ui.pages.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterProfilePageViewModel : ViewModel() {
    private val _selectedAvatar = MutableStateFlow<Int?>(null)
    val selectedAvatar: StateFlow<Int?> get() = _selectedAvatar

    fun selectAvatar(avatarId: Int) {
        _selectedAvatar.value = avatarId
    }

    fun confirmSelection(): Int? {
        return _selectedAvatar.value
    }
}
