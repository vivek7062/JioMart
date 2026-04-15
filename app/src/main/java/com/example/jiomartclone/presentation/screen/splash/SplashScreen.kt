package com.example.jiomartclone.presentation.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.jiomartclone.presentation.navigation.Routes


@Composable
fun SplashScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val startDestination by viewModel.startDestination.collectAsState()
    LaunchedEffect(startDestination) {
        startDestination?.let {
            navController.navigate(it) {
                popUpTo(Routes.Splash.route) {
                    inclusive = true
                }
            }
        }
    }
    Box(
        modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF58529),
                        Color(0xFFDD2A7B),
                        Color(0xFF8134AF)
                    )
                )
            )
    ) {
        Text(
            text = "Tweets",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge.copy(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xff000000),
                        Color(0xffffffff)
                    )
                )
            ),
            modifier = modifier.align(Alignment.Center)
        )
        Box(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
                .width(80.dp)
                .height(4.dp)
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xff000000),
                            Color(0xffffffff)
                        )
                    ), RoundedCornerShape(4.dp)
                )
        )
    }
}