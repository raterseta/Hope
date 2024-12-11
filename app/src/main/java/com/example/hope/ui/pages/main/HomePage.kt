//Tapi BottomNavnya hilang. Saya ingin balik ke HomeChatPage, tetapi lewat sini agar tetap ada bottom navbarnya

package com.example.hope.ui.pages.main

import androidx.compose.foundation.layout.PaddingValues
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
<<<<<<< HEAD
import androidx.navigation.NavController
=======
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
>>>>>>> 7d73c3b58825abd1bda380f33bf5f9d34a28711e
import com.example.hope.R
import com.example.hope.chat.HomeChatPage
import com.example.hope.ui.composables.bottomNav.BottomNavComposable
import com.example.hope.ui.composables.post.DummyPostData
import com.example.hope.ui.composables.post.PostComposable
import com.example.hope.ui.composables.post.getDummyPosts
import com.example.hope.ui.composables.post.getSavedPosts
import com.example.hope.ui.composables.topNav.SimpleTopNavComposable
import com.example.hope.ui.composables.topNav.TopNavComposable
import com.example.hope.ui.composables.topNav.TopNavViewModel
import com.example.hope.ui.pages.upload.UploadPage

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    navController: NavController, // Tambahkan navController sebagai parameter
    initialTab: Screen = Screen.Home // Tambahkan parameter untuk tab awal
) {
    var currentScreen by remember { mutableStateOf(initialTab) }

    val posts = getDummyPosts()
    val savedPosts = getSavedPosts()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavComposable(
                selectedScreen = currentScreen,
                onItemSelected = { selectedScreen ->
                    currentScreen = selectedScreen
                }
            )
        },
        topBar = {
            when (currentScreen) {
                Screen.Home -> TopNavComposable(
                    onProfileClick = onProfileClick,
                    onSearch = { },
                    onFilterClick = { }
                )
                Screen.Add -> {}
                Screen.Chat -> {}
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
            Screen.Chat -> HomeChatPage(
                modifier = Modifier.padding(innerPadding),
                navController = navController // Teruskan navController ke HomeChatPage
            )
            Screen.Bookmark -> SavedPostList(posts = savedPosts, modifier = Modifier.padding(innerPadding))
        }
    }
}


@Composable
fun PostList(posts: List<DummyPostData>, modifier: Modifier = Modifier) {
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
