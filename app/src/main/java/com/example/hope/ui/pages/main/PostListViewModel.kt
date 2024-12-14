package com.example.hope.ui.pages.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class PostListViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""


    fun toggleBookmark(postID: String) {
        val bookmarksRef = database.getReference("Bookmarks/$userID/$postID")

        bookmarksRef.get().addOnSuccessListener { snapshot ->
            val isBookmarked = snapshot.getValue(Boolean::class.java) ?: false
            bookmarksRef.setValue(!isBookmarked)
                .addOnSuccessListener {
                    Log.d("Bookmark", "Bookmark status toggled for post $postID")
                }
                .addOnFailureListener { exception ->
                    Log.e("Bookmark", "Failed to toggle bookmark: ${exception.message}")
                }
        }.addOnFailureListener { exception ->
            Log.e("Bookmark", "Failed to read bookmark status: ${exception.message}")
        }
    }
}

