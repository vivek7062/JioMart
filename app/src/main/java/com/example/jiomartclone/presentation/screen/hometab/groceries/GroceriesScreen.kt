package com.example.jiomartclone.presentation.screen.hometab.lowprice.groceries

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.jiomartclone.domain.model.groceries.GroceriesBanner
import com.example.jiomartclone.domain.model.groceries.GroceriesCategoryItem
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import com.example.jiomartclone.presentation.screen.home.ShimmerBox
import com.example.jiomartclone.presentation.screen.hometab.groceries.GroceriesCategoryViewModel
import com.example.jiomartclone.presentation.screen.hometab.groceries.GroceriesIntent
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun GroceriesScreen(
    modifier: Modifier = Modifier,
    groceriesCategoryViewModel: GroceriesCategoryViewModel = hiltViewModel(),
) {
    val state by groceriesCategoryViewModel.state.collectAsStateWithLifecycle()
    val isLoading = state.isLoading
    val errorMessage = state.error
    LaunchedEffect(key1 = Unit) {
        groceriesCategoryViewModel.onIntent(GroceriesIntent.LoadData)
    }
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.background(Color.LightGray.copy(alpha = 0.1f)),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            item {
                state.groceryBannerUiState.data?.let { bannerList ->
                    ShowGroceriesBanner(bannerList = bannerList)
                }
            }
            state.groceryCategoryUiState.data?.let { groceriesCategory ->
                groceriesCategory.forEach { category ->
                    item {
                        Text(
                            text = category.title,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                    }

                    items(category.items.chunked(4)) { rowItems ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowItems.forEach { item ->
                                Box(modifier = Modifier.weight(1f)) {
                                    GroceriesCategoryItemScreen(
                                        groceriesCategoryItem = item
                                    )
                                }
                            }
                            repeat(4 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
        if(isLoading){
            GroceriesListShimmer()
        }

        if(!isLoading && errorMessage!=null){
            ShowErrorScreen(message = errorMessage) {
                groceriesCategoryViewModel.onIntent(GroceriesIntent.Retry)
            }
        }
    }
}

@Composable
fun GroceriesCategoryItemScreen(
    modifier: Modifier = Modifier,
    groceriesCategoryItem: GroceriesCategoryItem,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(groceriesCategoryItem.image)
            .crossfade(true)
            .build()
    )

    Column(
        modifier = modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            if (painter.state is AsyncImagePainter.State.Loading) {
                ShimmerBox(
                    modifier = Modifier.matchParentSize()
                )
            }

            Image(
                painter = painter,
                contentDescription = groceriesCategoryItem.id,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = groceriesCategoryItem.title,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.titleSmall.copy(
                lineHeight = 16.sp,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun ShowGroceriesBanner(modifier: Modifier = Modifier, bannerList: List<GroceriesBanner>) {
    LazyRow(
        modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(bannerList) { banner ->
            GroceriesBannerItem(banner = banner)
        }
    }
}

@Composable
fun GroceriesBannerItem(modifier: Modifier = Modifier, banner: GroceriesBanner) {
    Box(
        modifier = modifier
            .width(110.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp, Color.Black,
                RoundedCornerShape(8.dp)
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(banner.image),
            contentDescription = banner.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun GroceriesBannerShimmer() {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(4) {
            ShimmerBox(
                modifier = Modifier
                    .width(110.dp)
                    .height(180.dp)
            )
        }
    }
}

@Composable
fun GroceriesItemShimmer() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        ShimmerBox(
            modifier = Modifier
                .size(80.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        ShimmerBox(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.8f)
        )
    }
}

@Composable
fun GroceriesListShimmer() {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {

        item {
            GroceriesBannerShimmer()
        }

        repeat(5) {

            item {
                ShimmerBox(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .height(16.dp)
                        .fillMaxWidth(0.4f)
                )
            }

            items(3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(4) {
                        Box(modifier = Modifier.weight(1f)) {
                            GroceriesItemShimmer()
                        }
                    }
                }
            }
        }
    }
}

