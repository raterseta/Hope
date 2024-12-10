package com.example.hope.ui.pages.upload

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hope.ui.composables.ButtonComposable
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun UploadPage(modifier: Modifier = Modifier, innerPadding: PaddingValues) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), // Menambahkan padding dari Scaffold
//        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize() // Mengisi seluruh halaman
        ) {
            // Tulisan "Postingan Baru!" di tengah
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Postingan Baru!",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black
                )
            }

            Text(
                text = "Jenis Postingan",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                ButtonComposable(
                    "Komunitas",
                    onClick = { TODO() },
                    isHighlighted = true,
                    modifier = Modifier.weight(1f)
                )
                ButtonComposable(
                    "Artikel",
                    onClick = { TODO() },
                    isHighlighted = false,
                    modifier = Modifier.weight(1f)
                )
            }

            // IconButton dengan ukuran lebih besar dan berbentuk kotak
            IconButton(
                onClick = { TODO() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Tinggi ditambah untuk membuatnya lebih besar
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Image",
                    modifier = Modifier.size(48.dp) // Ukuran icon lebih besar
                )
            }

            // TextField Judul
            TextField(
                value = "",
                onValueChange = { TODO() },
                label = { Text("Judul") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                singleLine = true,
                shape = RoundedCornerShape(35),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = modifier.height(16.dp))

            // TextField Lokasi
            TextField(
                value = "",
                onValueChange = { TODO() },
                label = { Text("Lokasi") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                singleLine = true,
                shape = RoundedCornerShape(35),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = modifier.height(16.dp))

            // TextField Deskripsi mengisi sisa ruang
            TextField(
                value = "",
                onValueChange = { TODO() },
                label = { Text("Deskripsi") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Mengisi sisa ruang
                    .padding(bottom = 16.dp),
                singleLine = false, // Bisa multi-line untuk deskripsi panjang
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonComposable(
                    text = "Upload",
                    onClick = { TODO() },
                    isHighlighted = true, // Sesuai desain untuk button utama
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}



@Preview
@Composable
private fun UploadPagePrev() {
    UploadPage(
        innerPadding = PaddingValues(0.dp)
    )
}