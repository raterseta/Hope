package com.example.hope.ui.composables.post

import androidx.compose.ui.graphics.painter.Painter
import com.example.hope.ui.pages.register.UserData

data class PostData(
    val postID: String,
    val userID: String,
    val postP: Painter,
    val title: String,
    val description: String,
    val isBookmarked: Boolean
)