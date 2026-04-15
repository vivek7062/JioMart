package com.example.jiomartclone.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldPassword(modifier: Modifier = Modifier, value: String, onValueChange:(String)-> Unit) {
    var visiblePassword by remember { mutableStateOf(false) }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        onValueChange = onValueChange,
        value = value,
        shape = RoundedCornerShape(8.dp),
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(0.2f),
            unfocusedContainerColor = Color.White.copy(0.2f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Password,
                contentDescription = "User",
                tint = Color.White
            )
        },
        visualTransformation = if (!visiblePassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            val icon =
                if (!visiblePassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = { visiblePassword = !visiblePassword }) {
                Icon(
                    icon,
                    contentDescription = "password Toggle",
                    tint = Color.White
                )
            }
        }
    )
}