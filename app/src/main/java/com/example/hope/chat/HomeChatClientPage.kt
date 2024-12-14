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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hope.R
import com.example.hope.ui.composables.template.ProfileField
import com.example.hope.ui.composables.topNav.TopNavChatAnotherUserComposable
import com.example.hope.ui.composables.topNav.TopNavChatComposable
import com.example.hope.ui.composables.topNav.TopNavViewModel
import com.google.gson.Gson
import java.util.UUID

//import com.example.hope.ui.composables.topNav.TopNavChatAnotherUserComposable


@Composable
fun HomeChatClientPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    generatePsikologViewModel: GeneratePsikologViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    currentUserViewModel: TopNavViewModel = viewModel(),

    ) {
    val poppins_bold = FontFamily(Font(R.font.poppins_bold))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeWidth = configuration.screenWidthDp.sp

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White

    val activePsikolog by generatePsikologViewModel.activePsikolog.collectAsState()
    val currentUser by currentUserViewModel.userProfile.collectAsState()

    val chatId = UUID.randomUUID().toString()



    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        // Top navigation
        TopNavChatAnotherUserComposable(currentUser)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = screenWidth * 0.05f, vertical = screenWidth * 0.03f)
        ) {
            // Display active psychologist if available
            if (activePsikolog != null && activePsikolog!!.avatarID != null) {
//                val activePsikologID = activePsikolog?.userID
                Column(
                    modifier = Modifier
                        .background(Color(0xFFDEE5D4), shape = RoundedCornerShape(screenWidth * 0.03f))
                        .fillMaxWidth()
                        .clickable {
                            // Convert activePsikolog to JSON string for navigation
                            val gson= Gson()
                            val activePsikologJson = gson.toJson(activePsikolog)

//                             Navigate to ContentChatPage, passing both chatId and serialized activePsikolog
//                            navController.navigate("homePage")
                            navController.navigate("chatClienttoPsikologPage/$chatId/$activePsikologJson")

                        }

                        .padding(screenWidth * 0.05f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val avatarID = activePsikolog?.avatarID?: R.drawable.avatar1 // Fallback ke avatar default
                        //STATIK -> ERROR RENDERING AVATARID TO IMAGE
                        Image(
                            painter = painterResource(id = avatarID),
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
                onClick = { generatePsikologViewModel.generateActivePsikolog() },
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





