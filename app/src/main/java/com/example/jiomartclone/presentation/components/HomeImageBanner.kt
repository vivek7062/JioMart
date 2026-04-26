package com.example.jiomartclone.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.jiomartclone.domain.model.HomeLowPriceBanner
import com.example.jiomartclone.presentation.screen.home.ShimmerBox
import kotlinx.coroutines.delay

@Composable
fun HomeImageBanner(modifier: Modifier = Modifier, banners: List<HomeLowPriceBanner>) {
    val pagerState = rememberPagerState(pageCount = { banners.size })
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) { page ->
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(banners[page].url)
                    .crossfade(true)
                    .build(),

                contentDescription = null,
                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),

                loading = {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                },

                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(banners.size) { index ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (selected) 10.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (selected) Color(0xFF00A859) else Color.Gray.copy(alpha = 0.5f))
                )
            }
        }
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(3000)
                val nextPage = (pagerState.currentPage + 1) % banners.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

}