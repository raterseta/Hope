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
        val usersRef = database.getReference("users")

        postsRef.get().addOnSuccessListener { snapshot ->
            val posts = snapshot.children.mapNotNull { it.getValue(PostData::class.java) }

            // Daftar untuk menyimpan semua tugas pengambilan data user
            val postUpdates = posts.map { post ->
                val userRef = usersRef.child(post.userID)
                userRef.get().continueWith { task ->
                    if (task.isSuccessful) {
                        val userSnapshot = task.result
                        val username = userSnapshot.child("username").getValue(String::class.java) ?: "Unknown"
                        val profilePicture = userSnapshot.child("avatarID").getValue(Int::class.java) ?: null

                        // Perbarui data post
                        post.username = username
                        post.profilePicture = profilePicture
                    }
                    post
                }
            }

            // Tunggu semua pembaruan selesai
            postUpdates.forEach { task ->
                task.addOnSuccessListener {
                    if (postUpdates.all { it.isSuccessful }) {
                        _postList.value = postUpdates.mapNotNull { it.result }
                    }
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("HomePageViewModel", "Error fetching posts: ${exception.message}")
        }
    }


}