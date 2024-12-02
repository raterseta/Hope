package com.example.hope.ui.composables.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import com.example.hope.R

sealed class BottomNavItem(
    var title: String,
    var selectedIcon: Int ,
    var unselectedIcon: Int
){
    data object Home :
        BottomNavItem(
            "Home",
            R.drawable.home_filled,
            R.drawable.home_outlined
        )
    data object Add :
        BottomNavItem(
            "Upload",
            R.drawable.add_filled,
            R.drawable.add_outlined
        )
    data object Chat :
        BottomNavItem(
            "Chat",
            R.drawable.chat_filled,
            R.drawable.chat_outlined
        )
    data object Bookmark :
        BottomNavItem(
            "Saved",
            R.drawable.bookmark_filled,
            R.drawable.bookmark_outlined
        )
}