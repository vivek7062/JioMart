package com.example.jiomartclone.presentation.screen.tweetlist


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun TweetListByCategory(
    category: String,
    navController: NavHostController,
    tweetListViewModel: TweetListViewModel = hiltViewModel(),
) {
    val state by tweetListViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        tweetListViewModel.getTweetList(category)
    }
    when {
        state.homeUiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        state.homeUiState.error != null -> {
            Text(text = state.homeUiState.error.toString(), color = Color.Red)
        }

        else -> {
            LazyColumn {
                items(state.homeUiState.data ?: emptyList()) { item ->
                    Text(text = item.toString())
                }
            }
        }
    }
}