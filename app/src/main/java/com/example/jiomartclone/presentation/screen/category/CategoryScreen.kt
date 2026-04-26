package com.example.jiomartclone.presentation.screen.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import com.example.jiomartclone.presentation.screen.cart.CartViewModel
import com.example.jiomartclone.presentation.screen.home.ShimmerBox
import com.example.jiomartclone.presentation.screen.hometab.lowprice.ProductItem

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    category: String,
    viewModel: ProductByCategoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()

    val cartMap = remember(cartItems) {
        cartItems.associateBy { it.productId }
    }

    val gridState = rememberLazyGridState()
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(category) {
        viewModel.getProductsByCategory(category)
    }
    when {
        state.productUiState.isLoading -> {
            ProductGridShimmer()
        }

        state.productUiState.error != null -> {
            ShowErrorScreen(
                message = state.productUiState.error ?: "Something went wrong"
            ) {
                viewModel.getProductsByCategory(category)
            }
        }

        else -> {
            val products = state.productUiState.data?.products.orEmpty()

            LazyVerticalGrid(
                modifier = modifier,
                state = gridState,
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 100.dp
                )
            ) {

                items(
                    items = products,
                    key = { it.productId }
                ) { item ->

                    val quantity = cartMap[item.productId]?.quantity ?: 0

                    ProductItem(
                        product = item,
                        quantity = quantity,
                        onAdd = {
                            cartViewModel.addToCart(item)
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        },
                        onRemove = {
                            cartViewModel.removeFromCart(item.productId)
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductGridShimmer() {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {

        items(9) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                )
                Spacer(modifier = Modifier.height(6.dp))

                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                ShimmerBox(
                    modifier = Modifier
                        .width(40.dp)
                        .height(12.dp)
                )
            }
        }
    }
}