package com.example.jiomartclone.presentation.screen.hometab.electronics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jiomartclone.domain.model.electronics.Electronics
import com.example.jiomartclone.domain.model.electronics.ElectronicsItem
import androidx.core.graphics.toColorInt

@Composable
fun ElectronicsBestCategory(electronics: Electronics, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(color = Color(0xFFD2F5F4))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = electronics.title,
                fontSize = 32.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontStyle = FontStyle.Italic,
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeight = 30.sp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2BB3B9),
                            Color(0xFF0E9AA7)
                        )
                    )
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Image(
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp),
                    painter = rememberAsyncImagePainter(electronics.headerImage),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = electronics.items ?: emptyList()) { item ->
                ElectronicBestItem(item = item)
            }
        }
    }
}

@Composable
fun ElectronicBestItem(modifier: Modifier = Modifier, item: ElectronicsItem) {
    Column(
        modifier = modifier
            .width(120.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = item.title,
            color = Color(0xFF384085),
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            style = MaterialTheme.typography.titleSmall.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                lineHeight = 8.sp,
            ),
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            painter = rememberAsyncImagePainter(item.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2BB3B9),
                            Color(0xFF0E9AA7)
                        )
                    )
                ),
            text = item.cta ?: "",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
fun ElectronicsDeals(modifier: Modifier = Modifier, electronics: Electronics) {
    val firstColor = electronics.bgColor?.split(',')[0] ?: "#FF8ED0F5"
    val secondColor = electronics.bgColor?.split(',')[1] ?: "#FF6BBBEA"
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        firstColor.toColorSafe(),
                        secondColor.toColorSafe()
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.7f)) {
                Text(
                    text = electronics.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = electronics.subtitle ?: "",
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .drawBehind {
                            drawRoundRect(
                                color = Color.Gray,
                                cornerRadius = CornerRadius(20.dp.toPx()),
                                style = Stroke(
                                    width = 2.dp.toPx(),
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(10f, 6f), 0f
                                    )
                                )
                            )
                        }
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 8.dp),
                    text = electronics.coupon?.text ?: "",
                    fontWeight = FontWeight.Normal,
                    fontSize = 9.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Box(modifier = Modifier.weight(0.3f), contentAlignment = Alignment.CenterEnd) {
                Image(
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp),
                    painter = rememberAsyncImagePainter(electronics.headerImage),
                    contentDescription = null
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val items = electronics.items ?: emptyList()

            items.forEach { item ->
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    ElectronicsDealsItem(
                        modifier = Modifier.fillMaxWidth(),
                        electronicsItem = item,
                        firstColor
                    )
                }
            }
        }

    }
}

@Composable
fun ElectronicsDealsItem(
    modifier: Modifier = Modifier,
    electronicsItem: ElectronicsItem,
    firstColor: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(2.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(
                   color = firstColor.toColorSafe()
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(electronicsItem.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = electronicsItem.subtitle ?: "",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )


        Text(
            text = electronicsItem.title,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                lineHeight = 12.sp
            )
        )
    }
}

@Composable
fun ShowBrands(modifier: Modifier = Modifier, brands: List<String>) {
    Column(modifier = modifier.padding(vertical = 16.dp)) {
        Text(
            text = "Brands You Love",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = brands) { brand ->
                BrandItem(brand = brand)
            }
        }
    }
}

@Composable
fun BrandItem(modifier: Modifier = Modifier, brand: String) {
    Box(
        modifier = modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, Color.LightGray, CircleShape),
        contentAlignment = Alignment.Center
    )
    {
        Box(
            modifier = modifier.
                size(65.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = brand.take(5),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun String.toColorSafe(): Color {
    return try {
        Color(this.toColorInt())
    } catch (e: Exception) {
        Color.Gray
    }
}