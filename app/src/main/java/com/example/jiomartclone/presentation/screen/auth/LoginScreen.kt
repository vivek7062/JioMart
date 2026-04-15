package com.example.jiomartclone.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.InsertLink
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jiomartclone.presentation.components.ButtonComponent
import com.example.jiomartclone.presentation.components.SocialIcon
import com.example.jiomartclone.presentation.components.TextFieldEmail
import com.example.jiomartclone.presentation.components.TextFieldPassword
import com.example.jiomartclone.presentation.navigation.Routes
import com.example.jiomartclone.presentation.screen.login.LoginViewModel

@Composable
fun Login(navController: NavHostController, modifier: Modifier = Modifier) {
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
        LoginCard(navController,modifier)
    }
}

@Composable
fun LoginCard(navController: NavHostController, modifier: Modifier = Modifier, loginViewModel: LoginViewModel = hiltViewModel()) {
    val state by loginViewModel.state.collectAsState()
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 36.dp)
        ) {
            Text(
                text = "Welcome Back!",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 36.sp,
                maxLines = 1,
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
                text = "Username",
                fontWeight = FontWeight.Medium,
                color = Color.LightGray,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )
            TextFieldEmail(modifier, state.email) {
                loginViewModel.onEmailChange(it)
            }
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text(
                text = "Password",
                fontWeight = FontWeight.Medium,
                color = Color.LightGray,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp,
                maxLines = 1,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )
            TextFieldPassword(value = state.password) {
                loginViewModel.onPasswordChange(it)
            }
            Text(
                text = "Forgot Password?",
                color = Color.LightGray,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = {

                    })
            )

            state.loginUiState.error?.let {
                Text(text = it, color = Color.Red)
            }

            state.loginUiState.data?.let {
                Text(text = "Login Successfully", color = Color.Green)
            }

            if (state.loginUiState.isLoading) {
                Text(text = "Loading...", color = Color.Blue)
            }

            ButtonComponent(modifier, true, "Sign In") {
                loginViewModel.login()
            }
            Text(
                text = "Sign Up Now?",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .align(Alignment.End)
                    .padding(horizontal = 16.dp)
                    .clickable {
                        navController.navigate(Routes.Signup.route){
                            popUpTo(Routes.Login.route){
                                inclusive = true
                            }
                        }
                    }
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Gray.copy(alpha = 0.5f)
                )
                Text(
                    text = "Or Continue With",
                    modifier = modifier.padding(horizontal = 8.dp),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                HorizontalDivider(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Gray.copy(alpha = 0.5f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SocialIcon(modifier, Icons.Default.Email) {

                }
                SocialIcon(modifier.padding(start = 16.dp), Icons.Default.Facebook) {

                }
                SocialIcon(modifier.padding(start = 16.dp), Icons.Filled.InsertLink) {

                }
            }

        }
    }
}

