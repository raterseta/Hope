package com.example.hope.ui.pages.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hope.chat.HomeChatPage
import com.example.hope.ui.composables.bottomNav.BottomNavComposable
import com.example.hope.ui.composables.post.DummyPostData
import com.example.hope.ui.composables.post.PostComposable
import com.example.hope.ui.composables.post.PostData
import com.example.hope.ui.composables.post.getSavedPosts
import com.example.hope.ui.composables.topNav.SimpleTopNavComposable
import com.example.hope.ui.composables.topNav.TopNavComposable
import com.example.hope.ui.pages.upload.UploadPage

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    viewModel: HomePageViewModel = viewModel()
) {
    var currentScreen by remember { mutableStateOf(Screen.Home) }
    viewModel.fetchPosts()
    val posts by viewModel.postList.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavComposable(
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
            Screen.Chat -> HomeChatPage(modifier = Modifier.padding(innerPadding))
            Screen.Bookmark -> {}
        }
    }
}


