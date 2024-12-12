package com.example.hope.ui.pages.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hope.ui.composables.post.PostComposable
import com.example.hope.ui.composables.post.PostData

@Composable
fun PostList(posts: List<PostData>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(posts) { post ->
            PostComposable(
                profilePicture = post.profilePicture,
                username = post.username,
                photo = post.postImg,
                title = post.title,
                description = post.description,
                isBookmarked = post.isBookmarked,
                onBookmarkClick = { /* Handle bookmark click */ }
            )
        }
    }
}