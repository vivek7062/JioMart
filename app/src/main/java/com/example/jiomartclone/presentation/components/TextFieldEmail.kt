package com.example.jiomartclone.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldEmail(modifier: Modifier = Modifier,value: String, placeholderText: String = "Tweets",onValueChange:(String) -> Unit) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        onValueChange = onValueChange,
        value = value,
        shape = RoundedCornerShape(8.dp),
        maxLines = 1,
        singleLine = true,
        placeholder = { Text(text = placeholderText, color = Color.LightGray) },
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
                imageVector = Icons.Default.Email,
                contentDescription = "User",
                tint = Color.White
            )
        }
    )
}