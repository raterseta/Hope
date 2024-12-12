package com.example.hope.ui.pages.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hope.ui.composables.post.PostData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomePageViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val _postList = MutableStateFlow<List<PostData>>(emptyList())
    val postList: StateFlow<List<PostData>> = _postList

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        val postsRef = database.getReference("Posts")
        val usersRef = database.getReference("Users")

        postsRef.get().addOnSuccessListener { snapshot ->
            val posts = snapshot.children.mapNotNull { it.getValue(PostData::class.java) }

            // Ambil data pengguna untuk setiap post berdasarkan userID
            posts.forEach { post ->
                val userRef = usersRef.child(post.userID)
                userRef.get().addOnSuccessListener { userSnapshot ->
                    val username = userSnapshot.child("username").getValue(String::class.java) ?: "Unknown"
                    val profilePicture = userSnapshot.child("avatarID").getValue(Int::class.java) ?: null

                    // Perbarui post dengan data pengguna
                    post.username = username
                    post.profilePicture = profilePicture

                    // Perbarui StateFlow jika diperlukan
                    _postList.value = posts
                }.addOnFailureListener { userException ->
                    Log.e("HomePageViewModel", "Error fetching user data: ${userException.message}")
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("HomePageViewModel", "Error fetching posts: ${exception.message}")
        }
    }


}
