package com.example.hope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hope.ui.composables.bottomNav.BottomNavComposable
import com.example.hope.ui.composables.topNav.TopNavComposable
import com.example.hope.ui.theme.HopeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HopeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        //contoh penggunaan BottomNav
                        BottomNavComposable()
                    },
                    topBar = {
                        TopNavComposable(
                            username = "Elaina",
                            profilePicture = R.drawable.elaina_stiker,
                            onProfileClick = {  },
                            onSearch = {  },
                        ) { }
                    }
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
//change1

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavComposable()
            },
            topBar = {
                TopNavComposable(
                    username = "Elaina",
                    profilePicture = R.drawable.elaina_stiker,
                    onProfileClick = { },
                    onSearch = {  },
                ) { }
            }
        ) { innerPadding ->
            Greeting(
                name = "Preview",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}