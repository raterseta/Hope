package com.example.hope.ui.pages.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.hope.ui.composables.post.PostData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UploadViewModel: ViewModel() {
    private val storage = FirebaseStorage.getInstance("gs://fir-auth-47d52.firebasestorage.app").getReference("HopePosts")
    private val database = FirebaseDatabase.getInstance().getReference("Posts")

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> get() = _location

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    fun onTitleChange(newTitle: String){
        _title.value = newTitle
    }

    fun onLocationChange(newLocation: String){
        _location.value = newLocation
    }

    fun onDescriptionChange(newDescription: String){
        _description.value = newDescription
    }

    fun UploadPost(
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onFailure("User not authenticated")
            return
        }

        if (imageUri == null) {
            onFailure("No image selected")
            return
        }

        val userId = currentUser.uid

        // Mengambil data pengguna dari Firebase Realtime Database
        val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
        userRef.get().addOnSuccessListener { userSnapshot ->
            if (!userSnapshot.exists()) {
                onFailure("User data not found in database")
                return@addOnSuccessListener
            }

            val username = userSnapshot.child("username").getValue(String::class.java) ?: "Unknown"
//            val avatarID = userSnapshot.child("avatarID").getValue(String::class.java)?.toIntOrNull() // Sesuaikan tipe datanya
            val avatarID = userSnapshot.child("avatarID").getValue(Int::class.java)

            if (avatarID == null) {
                onFailure("User profile picture not found")
                return@addOnSuccessListener
            }

            val fileName = "${System.currentTimeMillis()}_${imageUri.lastPathSegment}"
            val imgRef = storage.child(fileName)

            // Upload image
            imgRef.putFile(imageUri)
                .addOnSuccessListener {
                    // Get download URL
                    imgRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        val postId = database.push().key ?: return@addOnSuccessListener
                        val postData = PostData(
                            postID = postId,
                            userID = userId,
                            postImg = downloadUri.toString(),
                            title = _title.value,
                            location = _location.value,
                            description = _description.value,
                            isBookmarked = false,
                            username = username,
                            profilePicture = avatarID
                        )


                        // Simpan post data ke database
                        database.child(postId).setValue(postData)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                onFailure("Failed to save post: ${e.message}")
                            }
                    }.addOnFailureListener { e ->
                        onFailure("Failed to get download URL: ${e.message}")
                    }
                }
                .addOnFailureListener { e ->
                    onFailure("Failed to upload image: ${e.message}")
                }
        }.addOnFailureListener { e ->
            onFailure("Failed to get user data: ${e.message}")
        }
    }


}