package com.example.hope.ui.composables.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
    var isExpanded by remember { mutableStateOf(false) } // State untuk deskripsi

    val profilePicturePainter: Painter = profilePicture?.let {
        painterResource(it)
    } ?: painterResource(R.drawable.avatar3) // Pastikan `avatar3` ada di res/drawable

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
        // Header: Foto profil dan username
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

        // Gambar postingan
        Image(
            painter = postImagePainter,
            contentDescription = "Post Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.size(8.dp))

        // Deskripsi dan tombol bookmark
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
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isExpanded) description else description.take(100),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                if (description.length > 100) {
                    Text(
                        text = if (isExpanded) "Tampilkan lebih sedikit" else "Baca selengkapnya",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable { isExpanded = !isExpanded }
                            .padding(top = 4.dp)
                    )
                }
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
