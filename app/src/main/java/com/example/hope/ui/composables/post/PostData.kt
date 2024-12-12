package com.example.hope.ui.composables.post

data class PostData(
    val postID: String,
    val userID: String,
    val postImg: String,
    val title: String,
    val location: String,
    val description: String,
    val isBookmarked: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)