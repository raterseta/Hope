package com.example.hope.ui.composables.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hope.ui.pages.login.LoginPage


@Composable
fun CustomTextFieldWhite(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = true,
    togglePasswordVisibility: (() -> Unit)? = null
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        textStyle = textStyle,
        visualTransformation = if (isPasswordField && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.8f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray.copy(alpha = 1f),
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }
                    if (isPasswordField && togglePasswordVisibility != null) {
                        IconButton(
                            onClick = togglePasswordVisibility,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle Password Visibility"
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun CustomTextFieldGrey(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = true,
    togglePasswordVisibility: (() -> Unit)? = null
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        textStyle = textStyle,
        visualTransformation = if (isPasswordField && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(alpha = 0.6f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray.copy(alpha = 0.8f),
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }
                    if (isPasswordField && togglePasswordVisibility != null) {
                        IconButton(
                            onClick = togglePasswordVisibility,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle Password Visibility"
                            )
                        }
                    }
                }
            }
        }
    )
}

