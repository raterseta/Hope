package com.example.hope.ui.composables

import android.hardware.lights.Light
import android.widget.Button
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
    isHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isHighlighted) DarkBlue else White,
            contentColor = if (isHighlighted) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(50),
        modifier = modifier
            .widthIn(min = 150.dp)
            .padding(8.dp)
            .then(
                if (!isHighlighted) Modifier.border(
                    width = 2.dp,
                    color = DarkBlue,
                    shape = RoundedCornerShape(50)
                ) else Modifier
            )
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
        text = "apacoba aaf",
        onClick = { TODO() },
        isHighlighted = true
    )
}