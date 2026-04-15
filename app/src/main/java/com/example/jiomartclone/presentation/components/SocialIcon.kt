package com.example.jiomartclone.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SocialIcon(modifier: Modifier = Modifier, icon: ImageVector, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        modifier = modifier.size(48.dp),
        onClick = onClick
    ) {
        Icon(imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFAB0B0B),
            modifier= Modifier.size(16.dp))
    }
}