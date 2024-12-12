package com.example.hope.ui.pages.upload

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.hope.ui.composables.ButtonComposable

@Composable
fun UploadPage(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: UploadViewModel = viewModel()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), // Menambahkan padding dari Scaffold
//        color = Color.White
    ) {
        var selectedImgUri by remember { mutableStateOf<Uri?>(null)}

        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> selectedImgUri = uri }
        )
        val title by viewModel.title.collectAsState()
        val location by viewModel.location.collectAsState()
        val description by viewModel.description.collectAsState()

        val context = LocalContext.current

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize() // Mengisi seluruh halaman,
                .verticalScroll(scrollState)
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

            if (selectedImgUri == null) {
                IconButton(
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Image",
                        modifier = Modifier.size(48.dp)
                    )
                }
            } else {
                AsyncImage(
                    model = selectedImgUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Button(
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text("Change Photo")
                }
            }

            // IconButton dengan ukuran lebih besar dan berbentuk kotak


            // TextField Judul
            TextField(
                value = title,
                onValueChange = { viewModel.onTitleChange(it) },
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
                value = location,
                onValueChange = { viewModel.onLocationChange(it) },
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
                value = description,
                onValueChange = { viewModel.onDescriptionChange(it) },
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
                    onClick = {
                        if (selectedImgUri != null) {
                            viewModel.UploadPost(
                                imageUri = selectedImgUri,
                                onSuccess = {
                                    // Tampilkan pesan sukses
                                    Toast.makeText(context, "Post uploaded successfully", Toast.LENGTH_SHORT).show()
                                },
                                onFailure = { error ->
                                    // Tampilkan pesan error
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        } else {
                            Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                        }
                    },
                    isHighlighted = true,
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

}