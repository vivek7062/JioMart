package com.example.jiomartclone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jiomartclone.domain.model.HomeHeaderCategory
import com.example.jiomartclone.presentation.screen.hometab.electronics.toColorSafe

@Composable
fun CategoryHeaderTab(
    modifier: Modifier = Modifier,
    currentColor : String,
    selectedCategory: String,
    category: List<HomeHeaderCategory>,
    onSelected: (homeHeaderCategory: HomeHeaderCategory) -> Unit,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(color = currentColor.toColorSafe()),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(category) { item ->
            CategoryHeaderTabItem(
                selectedCategory,
                homeHeaderCategory = item,
                onSelected = onSelected
            )
        }
    }
}

@Composable
fun CategoryHeaderTabItem(
    selectedCategory: String,
    modifier: Modifier = Modifier,
    homeHeaderCategory: HomeHeaderCategory,
    onSelected: (homeHeaderCategory: HomeHeaderCategory) -> Unit,
) {
    val isSelected = homeHeaderCategory.id.toString() == selectedCategory
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable {
                onSelected(homeHeaderCategory)
            }) {

        Icon(
            imageVector = getImageVector(homeHeaderCategory.icon),
            contentDescription = "${homeHeaderCategory.id}",
            tint = if (isSelected) Color(0xffFFA500) else Color.White.copy(alpha = 0.7f)
        )

        Text(
            text = homeHeaderCategory.title,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .height(2.dp)
                .width(56.dp)
                .padding(horizontal = 2.dp)
                .background(
                    if (isSelected) Color(0xffFFA500) else Color.Transparent,
                    RoundedCornerShape(10.dp)
                )
        )
    }
}

fun getImageVector(url: String) = when (url) {
    "lowPrice" -> {
        Icons.Default.LocalOffer
    }

    "groceries" -> {
        Icons.Default.ShoppingCart
    }

    "electronics" -> {
        Icons.Default.PhoneAndroid
    }

    "coupons" -> {
        Icons.Default.ConfirmationNumber
    }

    "beauty" -> {
        Icons.Default.Face
    }

    else -> {
        Icons.Default.CheckCircle
    }
}