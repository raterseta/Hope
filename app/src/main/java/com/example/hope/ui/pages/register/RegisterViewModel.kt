package com.example.hope.ui.pages.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel: ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _confirmedPassword = MutableStateFlow("")
    val confirmedPassword: StateFlow<String> get() = _confirmedPassword

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> get() = _passwordVisible

    private val _confirmedPasswordVisible = MutableStateFlow(false)
    val confirmedPasswordVisible: StateFlow<Boolean> get() = _confirmedPasswordVisible

    private val _selectedAvatar = MutableStateFlow<Int?>(null)
    val selectedAvatar: StateFlow<Int?> get() = _selectedAvatar

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    //fun
    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmedPasswordChange(newConfirmedPassword: String) {
        _confirmedPassword.value = newConfirmedPassword
    }

    fun validatePasswordMatch(): Boolean {
        return _password.value == _confirmedPassword.value
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun toggleConfirmedPasswordVisibility() {
        _confirmedPasswordVisible.value = !_confirmedPasswordVisible.value
    }

    fun selectAvatar(avatarId: Int) {
        _selectedAvatar.value = avatarId
    }

    fun confirmSelection(): Int? {
        return _selectedAvatar.value
    }

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

    // Fungsi untuk mereset data
    fun resetData() {
        _username.value = ""
        _birthDate.value = ""
        _phoneNumber.value = ""
    }

    fun printUserData() {
        Log.d("RegisterViewModel", "Email: ${_email.value}")
        Log.d("RegisterViewModel", "Password: ${_password.value}")
        Log.d("RegisterViewModel", "Confirmed Password: ${_confirmedPassword.value}")
        Log.d("RegisterViewModel", "Username: ${_username.value}")
        Log.d("RegisterViewModel", "Birth Date: ${_birthDate.value}")
        Log.d("RegisterViewModel", "Phone Number: ${_phoneNumber.value}")
        Log.d("RegisterViewModel", "Selected Avatar: ${_selectedAvatar.value}")
    }

    //firebase
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init{
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        if(auth.currentUser == null){
            _authState.value = AuthState.Unauthenticated
        }else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun register(){
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }else if(!validatePasswordMatch()){
            _authState.value = AuthState.Error("Confirm password not same")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    val userID = auth.currentUser?.uid
                    if(userID != null && validateAndSubmitUserData()){
                        val database = FirebaseDatabase.getInstance()
                        val usersRef = database.getReference("users")
                        val userData = UserData(
                            userID = userID,
                            email = _email.value,
                            username = _username.value,
                            birthDate = _birthDate.value,
                            phoneNumber = _phoneNumber.value,
                            avatarID = _selectedAvatar.value
                        )
                        usersRef.child(userID).setValue(userData)
                            .addOnSuccessListener {
                                _authState.value = AuthState.Authenticated
                            }
                            .addOnFailureListener{ e ->
                                _authState.value = AuthState.Error("Failed to save user data: ${e.message}")
                            }
                    }else{
                        _authState.value = AuthState.Error("User ID is null")
                    }
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?: "Something went Wrong")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String): AuthState()
}