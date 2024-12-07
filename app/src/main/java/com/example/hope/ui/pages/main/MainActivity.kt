package com.example.hope.ui.pages.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hope.R
import com.example.hope.ui.composables.bottomNav.BottomNavComposable
import com.example.hope.ui.composables.post.DummyPostData
import com.example.hope.ui.composables.post.PostComposable
import com.example.hope.ui.composables.post.getDummyPosts
import com.example.hope.ui.composables.post.getSavedPosts
import com.example.hope.ui.composables.topNav.SimpleTopNavComposable
import com.example.hope.ui.composables.topNav.TopNavComposable
import com.example.hope.ui.pages.upload.UploadPage
import com.example.hope.ui.theme.HopeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HopeTheme {
                var currentScreen by remember { mutableStateOf(Screen.Home) }

                val posts = getDummyPosts()
                val savedPosts = getSavedPosts()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        //contoh penggunaan BottomNav
                        BottomNavComposable(
                            onItemSelected = { selectedScreen ->
                                currentScreen = selectedScreen
                            }
                        )
                    },
                    topBar = {
                        when (currentScreen) {
                            Screen.Home -> TopNavComposable(
                                username = "Elaina",
                                profilePicture = R.drawable.elaina_stiker,
                                onProfileClick = { },
                                onSearch = { },
                                onFilterClick = { }
                            )
                            Screen.Add -> {}
                            Screen.Chat -> {TODO()}
                            Screen.Bookmark -> SimpleTopNavComposable(
                                title = "Saved Posts",
                                onBackClick = { currentScreen = Screen.Home }
                            )
                        }
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        Screen.Home -> PostList(posts = posts, modifier = Modifier.padding(innerPadding))
                        Screen.Add -> UploadPage(innerPadding = innerPadding)
                        Screen.Chat -> TODO()
                        Screen.Bookmark -> SavedPostList(posts = savedPosts, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
//change1
@Composable
fun PostList(posts: List<DummyPostData>,  modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(posts) { post ->
            PostComposable(
                profilePicture = post.profilePicture,
                username = post.username,
                photo = post.photo,
                title = post.title,
                description = post.description,
                isBookmarked = post.isBookmarked,
                onBookmarkClick = { /* Handle bookmark click */ }
            )
        }
    }
}

@Composable
fun SavedPostList(posts: List<DummyPostData>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(posts) { post ->
            PostComposable(
                profilePicture = post.profilePicture,
                username = post.username,
                photo = post.photo,
                title = post.title,
                description = post.description,
                isBookmarked = post.isBookmarked,
                onBookmarkClick = { /* Handle bookmark click */ }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HopeTheme {
        var currentScreen by remember { mutableStateOf(Screen.Home) }

        val posts = getDummyPosts()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                //contoh penggunaan BottomNav
                BottomNavComposable(
                    onItemSelected = { selectedScreen ->
                        currentScreen == selectedScreen
                    }
                )
            },
            topBar = {
                when (currentScreen) {
                    Screen.Home -> TopNavComposable(
                        username = "Elaina",
                        profilePicture = R.drawable.elaina_stiker,
                        onProfileClick = { },
                        onSearch = { },
                        onFilterClick = { }
                    )
                    Screen.Add -> TopNavComposable(
                        username = "Elaina",
                        profilePicture = R.drawable.elaina_stiker,
                        onProfileClick = { },
                        onSearch = { },
                        onFilterClick = { }
                    )
                    Screen.Chat -> TopNavComposable(
                        username = "Elaina",
                        profilePicture = R.drawable.elaina_stiker,
                        onProfileClick = { },
                        onSearch = { },
                        onFilterClick = { }
                    )
                    Screen.Bookmark -> SimpleTopNavComposable(
                        title = "Saved Posts",
                        onBackClick = { currentScreen = Screen.Home }
                    )
                }
            }
        ) { innerPadding ->
            PostList(posts = posts,
                modifier = Modifier.padding(innerPadding))
        }
    }
}