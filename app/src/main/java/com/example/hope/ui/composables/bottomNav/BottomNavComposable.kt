package com.example.hope.ui.composables.bottomNav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hope.ui.pages.main.Screen
import com.example.hope.ui.theme.DarkBlue
import com.example.hope.ui.theme.LightBlue
import com.example.hope.ui.theme.White

@Composable
fun BottomNavComposable(
    modifier: Modifier = Modifier,
    onItemSelected: (Screen) -> Unit // Callback when an item is selected
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Add,
        BottomNavItem.Chat,
        BottomNavItem.Bookmark
    )

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Row(modifier = modifier) {
        items.forEachIndexed { index, item ->
            AddItem(
                screen = item,
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    onItemSelected(item.screen) // Notify parent to update screen
                }
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        //Text label
        label = {
            Text(text = screen.title,
                color = if(selected) LightBlue else White,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp
            )
        },
        //icon
        icon = {
            Icon(
                painter = painterResource(
                    id = if (selected) screen.selectedIcon else screen.unselectedIcon
                ),
                contentDescription = screen.title,
                tint =  if(selected) LightBlue else White
            )
        },
        selected = selected,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = LightBlue,
            unselectedIconColor = White,
            indicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .background(DarkBlue)
            .padding(0.dp)
    )
}



@Preview
@Composable
private fun BottomNavPreview() {
    BottomNavComposable(
        onItemSelected = {}
    )
}