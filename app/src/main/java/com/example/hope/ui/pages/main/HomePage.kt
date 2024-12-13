package com.example.hope.ui.pages.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hope.chat.HomeChatClientPage
import com.example.hope.chat.HomeChatPsikologPage
import com.example.hope.chat.HomeChatViewModel
import com.example.hope.ui.composables.bottomNav.BottomNavComposable
//import com.example.hope.ui.composables.post.PostList
import com.example.hope.ui.composables.post.getSavedPosts
import com.example.hope.ui.composables.topNav.SimpleTopNavComposable
import com.example.hope.ui.composables.topNav.TopNavComposable
import com.example.hope.ui.pages.upload.UploadPage

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    navController: NavController,
    initialTab: Screen = Screen.Home,
    homeChatViewModel: HomeChatViewModel = viewModel(),
    homePageViewModel: HomePageViewModel = viewModel()
) {
    var currentScreen by remember { mutableStateOf(initialTab) }

    // Mengambil data postingan
    homePageViewModel.fetchPosts()
    val posts by homePageViewModel.postList.collectAsState()

    // Mengambil role pengguna
    val userRole by homeChatViewModel.userRole.observeAsState()

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
            Screen.Chat -> {
                when (userRole) {
                    "Regular" -> HomeChatClientPage(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                    "Psikolog" -> HomeChatPsikologPage(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                    else -> Text("Loading...", modifier = Modifier.padding(innerPadding))
                }
            }
            Screen.Bookmark -> {
                val savedPosts = getSavedPosts()
                // Tampilkan halaman Bookmark (implementasi SavedPostList dapat ditambahkan sesuai kebutuhan)
                Text("Saved Posts", modifier = Modifier.padding(innerPadding))
            }
        }
    }
}