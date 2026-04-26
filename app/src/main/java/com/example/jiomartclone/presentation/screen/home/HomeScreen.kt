package com.example.jiomartclone.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.jiomartclone.presentation.components.CategoryHeaderTab
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import com.example.jiomartclone.presentation.screen.hometab.beauty.BeautyScreen
import com.example.jiomartclone.presentation.screen.hometab.coupons.CouponsScreen
import com.example.jiomartclone.presentation.screen.hometab.electronics.ElectronicsScreen
import com.example.jiomartclone.presentation.screen.hometab.lowprice.CategoryShimmer
import com.example.jiomartclone.presentation.screen.hometab.lowprice.LowPriceIntent
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

    LaunchedEffect(key1 = Unit) {
        viewModel.onIntent(HomeIntent.LoadData)
    }
    when {
        state.homeUiState.isLoading -> {
            Column {
                HomeHeaderShimmer()
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        ShimmerBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                    items(5) {
                        CategoryShimmer()
                    }
                }
            }
        }

        state.homeUiState.error != null -> {
            ShowErrorScreen(state.homeUiState.error!!) {
                viewModel.onIntent(HomeIntent.Retry)
            }
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

@Composable
fun HomeHeaderShimmer() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(6) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                ShimmerBox(
                    modifier = Modifier
                        .size(40.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                ShimmerBox(
                    modifier = Modifier
                        .height(10.dp)
                        .width(50.dp)
                )
            }
        }
    }
}