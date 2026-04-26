package com.example.jiomartclone.presentation.screen.hometab.lowprice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jiomartclone.data.local.auth.CartItem
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProductItem
import com.example.jiomartclone.presentation.screen.cart.CartViewModel

@Composable
fun CategoryListWithProduct(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel,
    cartItems: List<CartItem>,
    lowPriceCategoryWithProduct: LowPriceCategoryWithProduct,
    onCategoryClick: (String) -> Unit,
) {
    val haptic = LocalHapticFeedback.current
    val cartMap = remember(cartItems) {
        cartItems.associateBy { it.productId }
    }
    Column(modifier) {
        lowPriceCategoryWithProduct.forEach { category ->
            Column {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "View all",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF105874),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.clickable {
                            onCategoryClick(category.id)
                        }
                    )
                }
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = category.products,
                        key = { it.productId }
                    ) { product ->
                        val quantity = cartMap[product.productId]?.quantity ?: 0
                        ProductItem(
                            product = product,
                            quantity = quantity,
                            onAdd = {
                                cartViewModel.addToCart(product)
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            onRemove = {
                                cartViewModel.removeFromCart(product.productId)
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            })
                    }
                }
            }
        }
    }
}