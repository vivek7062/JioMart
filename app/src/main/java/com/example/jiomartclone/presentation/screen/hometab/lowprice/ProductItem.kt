package com.example.jiomartclone.presentation.screen.hometab.lowprice

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jiomartclone.R
import com.example.jiomartclone.domain.model.lowprice.Product


@Composable
fun ProductList(modifier: Modifier = Modifier, productList: List<Product>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(productList) { product ->
            ProductItem(product = product)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(modifier: Modifier = Modifier, product: Product) {
    val isWishList = rememberSaveable { mutableStateOf(product.isWishlist) }
    val selectQuantity = rememberSaveable { mutableIntStateOf(0) }

    Card(
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .width(130.dp)
            .height(254.dp),
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(150.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(product.productImageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    topStart = 4.dp,
                                    topEnd = 4.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            )
                    )
                    Text(
                        text = product.unit,
                        textAlign = TextAlign.Center,
                        color = Color(0xff3E3E3E),
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 4.dp,
                                    bottomEnd = 4.dp
                                )
                            )
                            .background(Color.LightGray.copy(alpha = 0.2f)),
                    )
                }
                Icon(
                    painter = painterResource(if (product.foodType == "veg") R.drawable.veg_icon else R.drawable.nonveg_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 30.dp, end = 10.dp)
                        .size(12.dp)

                )
                Icon(
                    imageVector = if (isWishList.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    tint = if (isWishList.value) Color(0xFFCB0000) else Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(20.dp)
                        .clickable {
                            isWishList.value = !isWishList.value
                        }
                )
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .padding(3.dp)
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = Color(0xFFE6F4FF))
                        .border(width = 1.dp, color = Color(0xFF0074B2), RoundedCornerShape(6.dp))
                ) {
                    AnimatedContent(targetState = selectQuantity.intValue) { qty ->
                        if (qty == 0) {
                            Text(
                                "+",
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .clickable { selectQuantity.intValue = 1 },
                                color = Color(0xFF0074B2)
                            )
                        } else {
                            Row(
                                modifier = Modifier.width(80.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    "−",
                                    fontSize = 18.sp,
                                    modifier = Modifier.clickable {
                                        if (selectQuantity.intValue > 0) {
                                            selectQuantity.intValue--
                                        }
                                    },
                                    color = Color(0xFF0074B2),
                                )
                                Text(qty.toString(), color = Color(0xFF000000))
                                Text(
                                    "+",
                                    modifier = Modifier.clickable { selectQuantity.intValue++ },
                                    color = Color(0xFF0074B2)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${product.productName} (${product.unit})", fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall.copy(
                    lineHeight = 14.sp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                color = Color(0xff3E3E3e),
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(30.dp)
            )
            Text(
                text = if (product.productDiscount > 0) "${product.productDiscount} %" else "",
                color = Color(0xFF388E3C),
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.titleSmall.copy(
                    lineHeight = 7.sp,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                fontSize = 10.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "₹${((product.productPrice * (100 - product.productDiscount) / 100))}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = if (product.productDiscount > 0) "₹${product.productPrice}" else "",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.labelSmall.copy(
                        textDecoration = TextDecoration.LineThrough,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    modifier = Modifier.padding(horizontal = 2.dp)
                )

            }
            Text(
                text = "Quick",
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.titleSmall.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeight = 10.sp,
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}