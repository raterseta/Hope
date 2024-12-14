package com.example.hope.ui.composables.post

data class PostData(
    var postID: String = "",
    var userID: String = "",
    var username: String = "",
    var profilePicture: Int? = null,
    var postImg: String = "",
    var title: String = "",
    var location: String = "",
    var description: String = "",
    var isBookmarked: Boolean = false
)
