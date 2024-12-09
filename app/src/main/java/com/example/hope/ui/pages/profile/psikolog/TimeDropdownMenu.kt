package com.example.hope.ui.pages.profile.psikolog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDropdownMenu(
    label: String,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    // Opsi waktu (misalnya, interval per jam)
    val timeOptions = listOf(
        "07:00","08:00","09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00","17:00","18:00"
    )

    // Menyimpan status menu dropdown dan waktu yang dipilih
    var expanded by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(selectedTime.ifEmpty { "08:00" }) } // Default "08:00" jika kosong

    Column(modifier = Modifier.fillMaxWidth()) {
        // Label
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        // ExposedDropdownMenuBox untuk dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            // TextField untuk menampilkan waktu yang dipilih
            TextField(
                value = currentTime,
                onValueChange = { /* Tidak ada aksi, karena hanya menggunakan dropdown */ },
                readOnly = true, // Membuat TextField hanya bisa dibaca
                modifier = Modifier
                    .menuAnchor() // Menghubungkan menu dengan TextField
                    .fillMaxWidth()
                    .padding(8.dp), // Memberikan padding
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) // Ikon dropdown
                },
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color.LightGray.copy(alpha = 0.2f),
//                    textColor = Color.Black,
//                    cursorColor = Color.Black
//                )
            )

            // Dropdown Menu untuk memilih waktu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                timeOptions.forEach { time ->
                    DropdownMenuItem(
                        onClick = {
                            currentTime = time
                            expanded = false
                            onTimeSelected(time) // Kirim waktu yang dipilih ke ViewModel
                        },
                        text = { Text(text = time) }
                    )
                }
            }
        }
    }
}

