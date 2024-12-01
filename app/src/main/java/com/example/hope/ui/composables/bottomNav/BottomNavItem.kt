package com.example.hope.ui.composables.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    var title: String,
    var selectedIcon: ImageVector,
    var unselectedIcon: ImageVector
){
    data object Home :
        BottomNavItem(
            "Home",
            Icons.Filled.Home,
            Icons.Outlined.Home
        )
    data object Add :
        BottomNavItem(
            "Add",
            Icons.Filled.Add,
            Icons.Outlined.Add
        )
    data object Chat :
        BottomNavItem(
            "Chat",
            Icons.Filled.Email,
            Icons.Outlined.Email
        )
    data object Bookmark :
        BottomNavItem(
            "Bookmark",
            Icons.Filled.Favorite,
            Icons.Outlined.Favorite
        )
}