package com.example.hope.ui.composables.bottomNav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavComposable(modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Add,
        BottomNavItem.Chat,
        BottomNavItem.Bookmark
    )

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Row {
        items.forEachIndexed { index, item ->
            AddItem(screen = item)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem
) {
    NavigationBarItem(
        //Text label
        label = {
            Text(text = screen.title)
        },
        //icon
        icon = {
            Icon(
                imageVector = screen.selectedIcon,
                contentDescription = screen.title,
                tint =  Color(0xFF8EACCD)
            )
        },
        selected = true,
        onClick = {/*TODO*/},
        colors = NavigationBarItemDefaults.colors(),
        modifier = Modifier
            .background(Color.Transparent)
            .padding(0.dp)
    )
}



@Preview
@Composable
private fun BottomNavPreview() {
    BottomNavComposable()
}