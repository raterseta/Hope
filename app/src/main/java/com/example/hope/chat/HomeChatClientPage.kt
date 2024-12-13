package com.example.hope.chat

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hope.R
import com.example.hope.ui.composables.template.ProfileField
import com.example.hope.ui.composables.topNav.TopNavChatAnotherUserComposable
import com.example.hope.ui.composables.topNav.TopNavChatComposable
import com.google.gson.Gson

//import com.example.hope.ui.composables.topNav.TopNavChatAnotherUserComposable


@Composable
fun HomeChatClientPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GeneratePsikologViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val poppins_regular = FontFamily(Font(R.font.poppins_regular))
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White

//    val currentTime by viewModel.currentTime.collectAsState() // Observe currentTime
    val activePsikolog by viewModel.activePsikolog.collectAsState()



    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        // Top navigation
        TopNavChatComposable()

//        TopNavChatAnotherUserComposable(activePsikolog)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = screenWidth * 0.05f, vertical = screenWidth * 0.03f)
        ) {
            // Display active psychologist if available
            if (activePsikolog != null && activePsikolog!!.avatarID != null) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFFDEE5D4), shape = RoundedCornerShape(screenWidth * 0.03f))
                        .fillMaxWidth()
//                        .clickable { navController.navigate("contentChat") }
                        .clickable {
                            val gson = Gson()
                            val psikologJson = gson.toJson(activePsikolog)
                            navController.navigate("contentChat/$psikologJson")
                        }

                        .padding(screenWidth * 0.05f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val avatarID = activePsikolog?.avatarID?: R.drawable.avatar1 // Fallback ke avatar default
                        Log.d("AvatarDebug", "Using avatar ID: $avatarID")
                        Log.d("Debug", "Active psikolog: $activePsikolog")
                        Log.d("Debug", "Avatar ID: ${activePsikolog?.avatarID}")
                        //STATIK -> ERROR RENDERING AVATARID TO IMAGE
                        Image(
                            painter = painterResource(id = R.drawable.avatar1),
                            contentDescription = "Psikolog Avatar",
                            modifier = Modifier
                                .size(screenWidth * 0.17f)
                                .clip(CircleShape)
                        )



                        Spacer(modifier = Modifier.width(screenWidth * 0.07f))

                        Column {
                            Text(
                                text = activePsikolog!!.username ?: "Unknown",
                                fontSize = sizeWidth * 0.045f,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontFamily = poppins_bold
                            )
                            ProfileField(
                                label = "Jadwal",
                                value = "${activePsikolog!!.psikologData?.startTime ?: "00:00"} - ${activePsikolog!!.psikologData?.endTime ?: "00:00"}"
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(screenWidth * 0.05f)
                                .clip(CircleShape)
                                .background(Color.Green)
                        )
                    }
                }
            } else {
                Text(
                    text = "No active psychologist available",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray
                )
            }
        }

        // Floating action button to generate a new psikolog
        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                onClick = { viewModel.generateActivePsikolog() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = screenWidth * 0.25f, end = screenWidth * 0.05f),
                shape = CircleShape,
                containerColor = Color(0xFFDEE5D4)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.reverse), // Reverse icon for the button
                    contentDescription = "Generate Psikolog",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}





