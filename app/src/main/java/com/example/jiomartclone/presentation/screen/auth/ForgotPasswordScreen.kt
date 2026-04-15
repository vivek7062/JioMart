package com.example.jiomartclone.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jiomartclone.presentation.components.ButtonComponent
import com.example.jiomartclone.presentation.components.TextFieldPassword


@Composable
fun ForGotPasswordScreen(navController: NavHostController, email: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF1D1E33),
                        Color(0xFF3A1C71),
                        Color(0xFF6A0572)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        ForgotCard(modifier)
    }
}

@Composable
fun ForgotCard(modifier: Modifier = Modifier) {
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(
                alpha = 0.1f
            )
        )
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter New Password",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 26.sp
            )
            Text(
                text = "Welcome back we missed you",
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.padding(top = 24.dp))
            Text(
                text = "New Password",
                fontWeight = FontWeight.Medium,
                color = Color.LightGray,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )
            TextFieldPassword(modifier, password.value) {
                password.value = it
            }
            Spacer(modifier = Modifier.padding(top = 24.dp))
            Text(
                text = "Confirm Password",
                fontWeight = FontWeight.Medium,
                color = Color.LightGray,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )
            TextFieldPassword(modifier, confirmPassword.value) {
                confirmPassword.value = it
            }

            ButtonComponent(modifier, true, "Continue") {

            }

        }
    }
}
