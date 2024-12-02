package com.example.hope.ui.composables

import android.hardware.lights.Light
import android.widget.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hope.ui.theme.DarkBlue
import com.example.hope.ui.theme.LightBlue
import com.example.hope.ui.theme.White

@Composable
fun ButtonComposable(
    text: String,
    onClick: () -> Unit,
    isHighlighted: Boolean
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isHighlighted) DarkBlue else White,
            contentColor = if (isHighlighted) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(8.dp)
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevButtonComposable() {
    ButtonComposable(
        text = "Click",
        onClick = { TODO() },
        isHighlighted = false
    )
}