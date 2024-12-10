package com.example.hope.ui.composables.post

import androidx.compose.ui.graphics.painter.Painter

data class PostData(
    val postID: String,
    val userID: String,
    val postP: Painter,
    val title: String,
    val description: String,
    val isBookmarked: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)