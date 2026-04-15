package com.example.jiomartclone.presentation.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
fun CustomToolbar(
    showSearch: Boolean,
    modifier: Modifier = Modifier,
    title: String,
    onBackHandle: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF6A1B9A),
                        Color(0xFF8E24AA)
                    )
                )
            )
            .padding(start = 16.dp, end = 16.dp, top = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.clickable {
                    onBackHandle()
                })
            Spacer(modifier = modifier.width(16.dp))
            Text(text = title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Black, modifier = Modifier.align(
            Alignment.CenterEnd).size(42.dp).padding(end = 16.dp))

    }
}