package com.example.hope.ui.composables.post

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.hope.R

data class DummyPostData(
    val profilePicture: Painter,
    val username: String,
    val photo: Painter,
    val title: String,
    val description: String,
    val isBookmarked: Boolean
)

@Composable
fun getDummyPosts(): List<DummyPostData> {
    return listOf(
        DummyPostData(
            profilePicture = painterResource(R.drawable.elaina_stiker), // Replace with your drawable resource
            username = "John Doe",
            photo = painterResource(R.drawable.shrimp), // Replace with your drawable resource
            title = "A beautiful sunset",
            description = "This is a description of the sunset post.",
            isBookmarked = false
        ),
        DummyPostData(
            profilePicture = painterResource(R.drawable.elaina_stiker),
            username = "Jane Smith",
            photo = painterResource(R.drawable.shrimp),
            title = "Exploring the mountains",
            description = "A beautiful hike up the mountains.",
            isBookmarked = true
        ),
        DummyPostData(
            profilePicture = painterResource(R.drawable.elaina_stiker),
            username = "Mark Taylor",
            photo = painterResource(R.drawable.shrimp),
            title = "City life",
            description = "The hustle and bustle of the city.",
            isBookmarked = false
        )
    )
}

@Composable
fun getSavedPosts(): List<DummyPostData> {
    return listOf(
        DummyPostData(
            profilePicture = painterResource(R.drawable.elaina_stiker), // Replace with your drawable resource
            username = "John Doe",
            photo = painterResource(R.drawable.shrimp), // Replace with your drawable resource
            title = "UDANG",
            description = "This is a description of the sunset post.",
            isBookmarked = false
        ),
        DummyPostData(
            profilePicture = painterResource(R.drawable.elaina_stiker),
            username = "UDANG Smith",
            photo = painterResource(R.drawable.shrimp),
            title = "Exploring the mountains",
            description = "A UDANG hike up the mountains.",
            isBookmarked = true
        ),
        DummyPostData(
            profilePicture = painterResource(R.drawable.elaina_stiker),
            username = "Mark UDANG",
            photo = painterResource(R.drawable.shrimp),
            title = "City life",
            description = "The hustle and UDANG of the city.",
            isBookmarked = false
        )
    )
}
