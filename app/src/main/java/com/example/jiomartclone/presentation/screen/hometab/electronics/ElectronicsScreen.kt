package com.example.jiomartclone.presentation.screen.hometab.electronics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProductItem
import com.example.jiomartclone.presentation.components.ShowAppLoader
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import com.example.jiomartclone.presentation.navigation.Routes
import com.example.jiomartclone.presentation.screen.hometab.groceries.GroceriesIntent
import com.example.jiomartclone.presentation.screen.hometab.lowprice.CategoryListWithProduct

@Composable
fun ElectronicsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    electronicsViewModel: ElectronicsViewModel = hiltViewModel(),
) {
    val state by electronicsViewModel.state.collectAsStateWithLifecycle()
    val isLoading = state.electronicsUiState.isLoading
    val errorMessage = state.electronicsUiState.error
    LaunchedEffect(key1 = Unit) {
        electronicsViewModel.onIntent(ElectronicsIntent.LoadData)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        state.electronicsUiState.data?.let {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                items(it) { electronics ->
                    when (electronics.type) {
                        "banner" -> {
                            ShowSingleBanner(url = electronics.headerImage?: "")
                        }

                        "product_horizontal" -> {
                            val lowPriceCategoryWithProduct = LowPriceCategoryWithProduct()
                            lowPriceCategoryWithProduct.add(
                                LowPriceCategoryWithProductItem(
                                    id = electronics.id,
                                    name = electronics.title,
                                    products = electronics.products ?: emptyList()
                                )
                            )
                            CategoryListWithProduct(
                                lowPriceCategoryWithProduct = lowPriceCategoryWithProduct
                            ) { category ->
                                navController.navigate(Routes.Category.createRoutes(category)) {
                                    popUpTo(Routes.Home.route) {
                                        inclusive = false
                                    }
                                }
                            }
                        }

                        "banner_cards" -> {
                            ElectronicsBestCategory(electronics)
                        }

                        "category_grid" -> {
                            ElectronicsDeals(electronics = electronics)
                        }

                        "brands" -> {
                            ShowBrands(brands = electronics.brand?: emptyList())
                        }
                    }
                }
            }
        }
        if (isLoading) {
            ShowAppLoader()
        }
        if (!isLoading && errorMessage != null) {
            ShowErrorScreen(message = errorMessage) {
                electronicsViewModel.onIntent(ElectronicsIntent.Retry)
            }
        }
    }
}

@Composable
fun ShowSingleBanner(modifier: Modifier = Modifier, url: String) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(175.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(url),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}