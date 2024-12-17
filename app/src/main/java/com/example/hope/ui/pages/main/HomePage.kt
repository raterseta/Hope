package com.example.hope.ui.pages.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hope.chat.*
import com.example.hope.ui.composables.bottomNav.BottomNavComposable
import com.example.hope.ui.composables.post.getSavedPosts
import com.example.hope.ui.composables.topNav.SimpleTopNavComposable
import com.example.hope.ui.composables.topNav.TopNavComposable
import com.example.hope.ui.pages.SearchComposable
import com.example.hope.ui.pages.upload.UploadPage

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    navController: NavController,
    initialTab: Screen = Screen.Home,
    getchRoleViewModel: GetchRoleViewModel = viewModel(),
    homePageViewModel: HomePageViewModel = viewModel()
) {
    var currentScreen by remember { mutableStateOf(initialTab) }


    val posts by homePageViewModel.postList.collectAsState()
    val savedPosts by homePageViewModel.savedPostList.collectAsState()

    val userRole by getchRoleViewModel.userRole.observeAsState()

    val searchedPost by homePageViewModel.savedPostList.collectAsState()

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
                    onSearch = { query ->
                        homePageViewModel.fetchSearchedPost(query) { searchedPosts ->
                            // Simpan hasil search ke SavedStateHandle
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("searchedPosts", searchedPosts)

                            // Navigasi ke SearchPage
                            navController.navigate("searchPage")
                        }
                    },
                )

                Screen.Bookmark -> SimpleTopNavComposable(
                    title = "Saved Posts",
                    onBackClick = { currentScreen = Screen.Home }
                )
                else -> {}
            }
        }
    ) { innerPadding ->
        when (currentScreen) {
            Screen.Home -> PostList(
                posts = posts,
                modifier = Modifier.padding(innerPadding),
            )
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
            Screen.Bookmark -> PostList(
                posts = savedPosts,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
