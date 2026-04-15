package com.example.jiomartclone.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.jiomartclone.presentation.components.CategoryHeaderTab
import com.example.jiomartclone.presentation.screen.hometab.beauty.BeautyScreen
import com.example.jiomartclone.presentation.screen.hometab.coupons.CouponsScreen
import com.example.jiomartclone.presentation.screen.hometab.electronics.ElectronicsScreen
import com.example.jiomartclone.presentation.screen.hometab.lowprice.LowPriceScreen
import com.example.jiomartclone.presentation.screen.hometab.lowprice.groceries.GroceriesScreen

@Composable
fun HomeScreen(
    navController: NavHostController,
    currentHeaderColor: MutableState<String>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val selectCategory = rememberSaveable { mutableStateOf("") }
    when {
        state.homeUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        state.homeUiState.error != null -> {
            Text(text = state.homeUiState.error.toString(), color = Color.Red)
        }

        else -> {
            LaunchedEffect(state.homeUiState.data) {
                if (!state.homeUiState.data.isNullOrEmpty() && selectCategory.value.isEmpty()) {
                    state.homeUiState.data?.let {
                        selectCategory.value = it[0].id.toString()
                    }

                }
            }
            Column {
                CategoryHeaderTab(
                    currentColor = currentHeaderColor.value,
                    selectedCategory = selectCategory.value,
                    category = state.homeUiState.data ?: emptyList()
                ) {
                    selectCategory.value = it.id.toString()
                }
                when (selectCategory.value) {
                    "1" -> {
                        currentHeaderColor.value = "#105874"
                        LowPriceScreen(navController)
                    }

                    "2" -> {
                        currentHeaderColor.value = "#6FCAC2"
                        GroceriesScreen()
                    }

                    "3" -> {
                        currentHeaderColor.value = "#D7EA6A"
                        ElectronicsScreen(navController)
                    }

                    "4" -> {
                        currentHeaderColor.value = "#EFB77F"
                        CouponsScreen()
                    }
                    "5" -> {
                        currentHeaderColor.value = "#58B3E4"
                        BeautyScreen()
                    }
                    else -> {}
                }
            }
        }
    }
}