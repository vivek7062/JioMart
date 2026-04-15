package com.example.jiomartclone.presentation.screen.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jiomartclone.presentation.components.ButtonComponent
import com.example.jiomartclone.presentation.navigation.Routes
import com.example.jiomartclone.presentation.screen.auth.otp.OtpUiState
import com.example.jiomartclone.presentation.screen.auth.otp.OtpViewModel
import kotlinx.coroutines.delay


    @Composable
    fun OtpScreen(navController: NavHostController, email:String, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
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
            OtpScreenCard(navController, email, modifier)
        }
    }

    @Composable
    fun OtpScreenCard(navController: NavHostController, email:String, modifier: Modifier = Modifier , otpViewModel: OtpViewModel = hiltViewModel()) {

        val state by otpViewModel.state.collectAsState()
        val initalTime  = 30
        var timerKey by remember { mutableStateOf(0) }
        var isEnabled by remember { mutableStateOf(false) }
        var timeLeft by remember { mutableStateOf(initalTime) }

        LaunchedEffect(email) {
            otpViewModel.onEmailChange(email)
        }

        LaunchedEffect(state.otp) {
            if (state.otp.toString().length == 4) {
                otpViewModel.validateOtp()
            }
        }

        LaunchedEffect(state.otpUiState.data) {
            if (state.otpUiState.data != null) {
                navController.navigate(Routes.Home.route) {
                    popUpTo(0)
                }
            }
        }
        LaunchedEffect(timerKey) {

            otpViewModel.resendOtp()
            isEnabled = false
            timeLeft = initalTime

            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }

            isEnabled = true
        }

        if (state.otpUiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(
                    alpha = 0.1f
                )
            ),
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Get Your Code",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 26.sp
                )
                Text(
                    text = "You will received otp at $email",
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.padding(top = 24.dp))
                AdvancedOtpField(state, otpViewModel)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Didn't receive code? ",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    if (isEnabled) {
                        Text(
                            text = "Resend Code",
                            color = Color(0xFFFFFFFF),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                timerKey++
                            }
                        )
                    } else {
                        Text(
                            text = "Resend in 00:${timeLeft.toString().padStart(2, '0')}",
                            color = Color.LightGray
                        )
                    }
                }
                ButtonComponent(modifier, true, "Verify And Proceed") {
                    otpViewModel.validateOtp()
                }

                state.otpUiState.error?.let {
                    Text(it, color = Color.Red)
                }
            }
        }
    }

@Composable
fun AdvancedOtpField(state: OtpUiState, otpViewModel: OtpViewModel) {

    val otp = if (state.otp == 0) "" else state.otp.toString()  // ✅ FIX

    BasicTextField(
        value = otp,
        onValueChange = {
            if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                otpViewModel.onOtpChange(it.toIntOrNull() ?: 0)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                repeat(4) { index ->

                    val char = otp.getOrNull(index)?.toString() ?: ""
                    val isFocused = otp.length == index

                    val scale by animateFloatAsState(
                        targetValue = if (isFocused) 1.1f else 1f,
                        label = ""
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(55.dp)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                            .border(
                                width = 2.dp,
                                color = when {
                                    otp.length > index -> Color(0xFF4CAF50)
                                    isFocused -> Color(0xFF6A1B9A)
                                    else -> Color.Gray
                                },
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {

                        if (isFocused && char.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(20.dp)
                                    .background(Color(0xFFFFFFFF))
                            )
                        }

                        Text(
                            text = char,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}