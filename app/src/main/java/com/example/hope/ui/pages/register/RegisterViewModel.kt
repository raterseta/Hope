package com.example.hope.ui.pages.register

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hope.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

    //firebase
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // MutableStateFlow to handle auth state
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        // You can update the MutableStateFlow here
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun register() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        } else if (!validatePasswordMatch()) {
            _authState.value = AuthState.Error("Confirm password not same")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

        private lateinit var googleSignInClient: GoogleSignInClient

        // Metode untuk membuat GoogleSignInClient
        fun createGoogleSignInClient(context: Context): GoogleSignInClient {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            return GoogleSignIn.getClient(context, gso)
        }

        // Metode untuk memproses hasil Google Sign-In
        fun handleGoogleSignInResult(data: Intent?, context: Context) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)

                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)

                // Autentikasi dengan Firebase
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            // Login berhasil
                            _authState.value = AuthState.Authenticated
                        } else {
                            // Login gagal
                            _authState.value = AuthState.Error(
                                authTask.exception?.message ?: "Google Sign-In failed"
                            )
                        }
                    }
            } catch (e: ApiException) {
                // Tangani error
                _authState.value = AuthState.Error("Google Sign-In failed: ${e.message}")
            }
        }

        // Metode untuk memulai proses Google Sign-In
        fun signInWithGoogle(context: Context, launcher: ActivityResultLauncher<Intent>) {
            googleSignInClient = createGoogleSignInClient(context)
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
}


sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String): AuthState()
}