package com.example.hope.ui.composables.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.hope.R

@Composable
fun PostComposable(
    profilePicture: Int?,
    username: String,
    photo: String,
    title: String,
    description: String,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit
) {
    var isImagePreviewVisible by remember { mutableStateOf(false) }
    var isTextExpanded by remember { mutableStateOf(false) }

    val profilePicturePainter: Painter = profilePicture?.let {
        painterResource(it)
    } ?: painterResource(R.drawable.avatar3)

    val postImagePainter = if (photo.isNotEmpty()) {
        rememberAsyncImagePainter(photo)
    } else {
        painterResource(R.drawable.avatar3) // Placeholder default
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = profilePicturePainter,
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Text(
                text = username,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Gambar dengan klik untuk preview
        Image(
            painter = postImagePainter,
            contentDescription = "Post Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clickable {
                    isImagePreviewVisible = true
                }
        )

        // Popup untuk gambar
        if (isImagePreviewVisible) {
            Dialog(onDismissRequest = { isImagePreviewVisible = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = postImagePainter,
                        contentDescription = "Preview Image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isImagePreviewVisible = false // Tutup popup
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f, true)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                // Deskripsi dengan teks ekspansi
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = if (isTextExpanded) Int.MAX_VALUE else 2,
                    overflow = if (isTextExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isTextExpanded = !isTextExpanded
                        }
                )
            }

            IconButton(onClick = onBookmarkClick) {
                Icon(
                    painter = painterResource(
                        id = if (isBookmarked) R.drawable.bookmark_filled else R.drawable.bookmark_outlined
                    ),
                    contentDescription = if (isBookmarked) "Remove Bookmark" else "Add Bookmark"
                )
            }
        }
    }
}


