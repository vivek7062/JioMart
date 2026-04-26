package com.example.jiomartclone.presentation.screen.hometab.electronics

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProductItem
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import com.example.jiomartclone.presentation.navigation.Routes
import com.example.jiomartclone.presentation.screen.cart.CartViewModel
import com.example.jiomartclone.presentation.screen.home.ShimmerBox
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
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()
    LaunchedEffect(true) {
        electronicsViewModel.onIntent(ElectronicsIntent.LoadData)
    }
    Box(modifier = modifier.fillMaxSize()) {

        when {
            isLoading -> {
                ElectronicsScreenShimmer()
            }

            errorMessage != null -> {
                ShowErrorScreen(message = errorMessage) {
                    electronicsViewModel.onIntent(ElectronicsIntent.Retry)
                }
            }

            else -> {
                state.electronicsUiState.data?.let { list ->

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 64.dp)
                    ) {

                        items(
                            items = list,
                            key = { it.id }
                        ) { electronics ->

                            when (electronics.type) {

                                "banner" -> {
                                    ShowSingleBanner(url = electronics.headerImage ?: "")
                                }

                                "product_horizontal" -> {

                                    val section = remember(electronics.id) {
                                        LowPriceCategoryWithProduct().apply {
                                            add(
                                                LowPriceCategoryWithProductItem(
                                                    id = electronics.id,
                                                    name = electronics.title,
                                                    products = electronics.products ?: emptyList()
                                                )
                                            )
                                        }
                                    }

                                    CategoryListWithProduct(
                                        lowPriceCategoryWithProduct = section,
                                        cartItems = cartItems,
                                        cartViewModel = cartViewModel
                                    ) { category ->
                                        navController.navigate(
                                            Routes.Category.createRoutes(category)
                                        )
                                    }
                                }

                                "banner_cards" -> {
                                    ElectronicsBestCategory(electronics)
                                }

                                "category_grid" -> {
                                    ElectronicsDeals(electronics= electronics)
                                }

                                "brands" -> {
                                    ShowBrands(brands = electronics.brand ?: emptyList())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSingleBanner(
    modifier: Modifier = Modifier,
    url: String
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build()
    )

    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(175.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {

        if (painter.state is AsyncImagePainter.State.Loading) {
            ShimmerBox(
                modifier = Modifier.matchParentSize()
            )
        }
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
fun ElectronicsBannerShimmer() {
    ShimmerBox(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(175.dp)
    )
}

@Composable
fun HorizontalProductShimmer() {

    Column {
        ShimmerBox(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                .height(16.dp)
                .fillMaxWidth(0.4f)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(5) {
                Column(
                    modifier = Modifier.width(120.dp)
                ) {
                    ShimmerBox(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShimmerBox(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun ElectronicsGridShimmer() {

    Column {

        ShimmerBox(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .height(16.dp)
                .fillMaxWidth(0.4f)
        )

        repeat(2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(4) {
                    Box(modifier = Modifier.weight(1f)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            ShimmerBox(modifier = Modifier.size(80.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            ShimmerBox(
                                modifier = Modifier
                                    .height(12.dp)
                                    .fillMaxWidth(0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BrandsShimmer() {
    Column {

        ShimmerBox(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .height(16.dp)
                .fillMaxWidth(0.3f)
        )

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(6) {
                ShimmerBox(
                    modifier = Modifier
                        .size(80.dp)
                )
            }
        }
    }
}

@Composable
fun ElectronicsScreenShimmer() {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 64.dp)
    ) {

        item { ElectronicsBannerShimmer() }

        item { HorizontalProductShimmer() }

        item { ElectronicsGridShimmer() }

        item { BrandsShimmer() }

        item { HorizontalProductShimmer() }
    }
}
