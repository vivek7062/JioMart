package com.example.jiomartclone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    text: String = "Login",
    onClick: () -> Unit
) {
    val gradient = Brush.horizontalGradient(
        colors =
            if (isEnabled) listOf(
                Color(0xFF8E2DE2),
                Color(0xFFD65DB1),
                Color(0xFFFF6F61)
            ) else listOf(
                Color.Gray,
                Color.LightGray
            )
    )
    Surface(
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
            .height(55.dp)
    ) {
        Box(
            modifier = Modifier.background(gradient),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}