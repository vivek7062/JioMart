package com.example.jiomartclone.presentation.screen.hometab.lowprice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jiomartclone.presentation.components.HomeImageBanner
import com.example.jiomartclone.presentation.components.ShowAppLoader
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import com.example.jiomartclone.presentation.navigation.Routes

@Composable
fun LowPriceScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    lowPriceViewModel: LowPriceViewModel = hiltViewModel(),
) {
    val state by lowPriceViewModel.state.collectAsStateWithLifecycle()
    val isLoading = state.isLoading
    val errorMessage = state.error

    LaunchedEffect(key1 = Unit) {
        lowPriceViewModel.onIntent(LowPriceIntent.LoadData)
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 120.dp)
        ) {

            item {
                state.homeUiState.data?.let {
                    HomeImageBanner(banners = it)
                }
            }

            item {
                state.categoryState.data?.let {
                    CategoryListWithProduct(
                        lowPriceCategoryWithProduct = it
                    ) { category ->
                        navController.navigate(Routes.Category.createRoutes(category)) {
                            popUpTo(Routes.Home.route) {
                                inclusive = false
                            }
                        }
                    }
                }
            }
        }
        if (isLoading) {
            ShowAppLoader()
        }
        if (!isLoading && errorMessage != null) {
            ShowErrorScreen(errorMessage) {
                lowPriceViewModel.onIntent(LowPriceIntent.Retry)
            }
        }
    }
}