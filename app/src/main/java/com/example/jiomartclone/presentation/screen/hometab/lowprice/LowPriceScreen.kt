package com.example.jiomartclone.presentation.screen.hometab.lowprice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.jiomartclone.presentation.screen.cart.CartViewModel
import com.example.jiomartclone.presentation.screen.home.ShimmerBox

@Composable
fun LowPriceScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    lowPriceViewModel: LowPriceViewModel = hiltViewModel(),
) {
    val state by lowPriceViewModel.state.collectAsStateWithLifecycle()
    val isLoading = state.isLoading
    val errorMessage = state.error
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()

    LaunchedEffect(key1 = Unit) {
        lowPriceViewModel.onIntent(LowPriceIntent.LoadData)
    }

    Box(modifier = modifier.fillMaxSize()) {

        when {
            isLoading -> {
                LazyColumn {
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

            errorMessage != null -> {
                ShowErrorScreen(errorMessage) {
                    lowPriceViewModel.onIntent(LowPriceIntent.Retry)
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 120.dp)
                ) {

                    item {
                        state.homeUiState.data?.let {
                            HomeImageBanner(banners = it)
                        }
                    }

                    item {
                        state.categoryState.data?.let {
                            CategoryListWithProduct(
                                cartViewModel = cartViewModel,
                                cartItems = cartItems,
                                lowPriceCategoryWithProduct = it
                            ) { category ->
                                navController.navigate(
                                    Routes.Category.createRoutes(category)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItemShimmer() {
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(254.dp)
    ) {

        ShimmerBox(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ShimmerBox(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        ShimmerBox(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.5f)
        )
    }
}
@Composable
fun CategoryShimmer() {

    Column {

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            ShimmerBox(
                modifier = Modifier
                    .padding(16.dp)
                    .height(16.dp)
                    .fillMaxWidth(0.4f)
            )

            ShimmerBox(
                modifier = Modifier
                    .padding(16.dp)
                    .height(16.dp)
                    .fillMaxWidth(0.4f)
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(6) {
                ProductItemShimmer()
            }
        }
    }
}