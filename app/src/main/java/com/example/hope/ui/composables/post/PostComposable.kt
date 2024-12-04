package com.example.hope.ui.composables.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hope.R

@Composable
fun PostComposable(
    profilePicture: Painter,
    username: String,
    photo: Painter,
    title: String,
    description: String,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit
) {
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
                painter = profilePicture,
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Text(
                text = username,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        Image(
            painter = photo,
            contentDescription = "post Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f,true)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2, // Batas deskripsi agar tidak memanjang
                    overflow = TextOverflow.Ellipsis, // Tambahkan ellipsis jika teks terlalu panjang
                    modifier = Modifier.fillMaxWidth()
                )
            }

            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier.align(Alignment.Top)
            ) {
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

@Preview(showBackground = true)
@Composable
fun PreviewPostComposable() {
    val dummyProfilePicture = painterResource(id = R.drawable.elaina_stiker) // Replace with your drawable
    val dummyUploadedPhoto = painterResource(id = R.drawable.shrimp) // Replace with your drawable

    var isBookmarked by remember { mutableStateOf(false) }

    PostComposable(
        profilePicture = dummyProfilePicture,
        username = "Elaina",
        photo = dummyUploadedPhoto,
        title = "Beautiful Shrimp",
        description = "Captured this amazing sunset during my trip to Bali!, so cute 10/10, will eat again,",
        isBookmarked = isBookmarked,
        onBookmarkClick = { isBookmarked = !isBookmarked }
    )
}