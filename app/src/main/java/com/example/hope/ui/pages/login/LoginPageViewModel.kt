package com.example.hope.ui.pages.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.hope.R
import com.example.hope.ui.pages.register.AuthState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginPageViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val username: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    val passwordVisible = mutableStateOf(false)

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var googleSignInClient: GoogleSignInClient

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

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

    // Metode lainnya tetap sama seperti sebelumnya...
    fun togglePasswordVisibility() {
        passwordVisible.value = !passwordVisible.value
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onEmailChange(newUsername: String) {
        _email.value = newUsername
    }

    fun login() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading

        // Login dengan email dan password
        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    // Metode untuk memulai proses Google Sign-In
    fun signInWithGoogle(context: Context, launcher: ActivityResultLauncher<Intent>) {
        googleSignInClient = createGoogleSignInClient(context)
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
}