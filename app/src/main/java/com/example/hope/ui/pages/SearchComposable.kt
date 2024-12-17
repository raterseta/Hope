package com.example.hope.ui.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hope.ui.composables.post.PostData
import com.example.hope.ui.composables.topNav.SimpleTopNavComposable
import com.example.hope.ui.pages.main.PostList

@Composable
fun SearchComposable(
    modifier: Modifier = Modifier,
    posts: List<PostData>,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            SimpleTopNavComposable(
                title = "Search Result" ,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        PostList(
            posts = posts,
            modifier = Modifier.padding(innerPadding)
        )
    }

}