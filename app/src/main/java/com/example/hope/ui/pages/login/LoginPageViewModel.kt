// LoginPageViewModel.kt
package com.example.hope.ui.pages.login

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
import com.google.firebase.database.FirebaseDatabase
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

    private var onUserNotFoundCallback: (() -> Unit)? = null

    private var loginFailed = false
    val forceChooseAccount = mutableStateOf(false)

    fun resetForceChooseAccount() {
        forceChooseAccount.value = false
    }

    // Tambahkan properti publik untuk mengakses nilai loginFailed
    val isLoginFailed: Boolean
        get() = loginFailed

    // Metode untuk membuat GoogleSignInClient
    fun createGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    // Metode untuk memproses hasil Google Sign-In
    fun handleGoogleSignInResult(
        data: Intent?,
        context: Context,
        onUserNotFound: () -> Unit
    ) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            if (account == null || account.idToken.isNullOrEmpty()) {
                _authState.value = AuthState.Error("Google Sign-In failed: Invalid account or ID token.")
                return
            }

            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            auth.signInWithCredential(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val currentUser = auth.currentUser
                        if (currentUser != null) {
                            checkUserInDatabase(currentUser.uid, onUserNotFound)
                        } else {
                            _authState.value = AuthState.Error("No user found after Google Sign-In.")
                        }
                    } else {
                        _authState.value = AuthState.Error(
                            authTask.exception?.message ?: "Google Sign-In failed during Firebase authentication."
                        )
                    }
                    // Reset forceChooseAccount setelah selesai
                    resetForceChooseAccount()
                }
        } catch (e: ApiException) {
            _authState.value = AuthState.Error("Google Sign-In failed: ${e.message}")
            loginFailed = true
            resetForceChooseAccount()
        }
    }

    // Metode untuk memeriksa keberadaan pengguna di database Firebase
    private fun checkUserInDatabase(userId: String, onUserNotFound: () -> Unit) {
        val databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child("users").child(userId).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    // Pengguna ditemukan di database
                    _authState.value = AuthState.Authenticated
                } else {
                    // Pengguna tidak ditemukan, arahkan ke halaman register
                    _authState.value = AuthState.Unauthenticated
                    onUserNotFound()  // Memanggil callback untuk memberi tahu user tidak ditemukan

                    // Reset proses pemilihan akun Google
                    forceChooseAccount.value = true

                    // Logout dari Firebase dan Google
                    FirebaseAuth.getInstance().signOut()  // Logout dari Firebase
                    googleSignInClient.signOut().addOnCompleteListener {
                        // Setelah logout, force pilih akun lagi
                        forceChooseAccount.value = true
                    }
                }
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error(
                    "Failed to check user in database: ${exception.message}"
                )
            }
    }

    // Metode untuk memulai proses Google Sign-In
    fun signInWithGoogle(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        forceChooseAccount: Boolean,
        onUserNotFound: () -> Unit
    ) {
        googleSignInClient = createGoogleSignInClient(context)

        if (forceChooseAccount) {
            // Revoke akses untuk memaksa pemilihan akun baru
            googleSignInClient.revokeAccess().addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }
        } else {
            // Luncurkan intent sign-in
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }


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
}
