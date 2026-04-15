package com.example.jiomartclone.presentation.screen.hometab.lowprice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProductItem

@Composable
fun CategoryListWithProduct(
    modifier: Modifier = Modifier,
    lowPriceCategoryWithProduct: LowPriceCategoryWithProduct,
    onCategoryClick:(String)-> Unit
) {

    Column(modifier) {
        lowPriceCategoryWithProduct.forEach { category ->
            CategoryItem(
                lowPriceCategoryWithProductItem = category,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    lowPriceCategoryWithProductItem: LowPriceCategoryWithProductItem,
    onCategoryClick:(String)-> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = lowPriceCategoryWithProductItem.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "View all",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF105874),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.clickable{
                    onCategoryClick(lowPriceCategoryWithProductItem.id)
                }
            )
        }
        ProductList(productList = lowPriceCategoryWithProductItem.products)
    }
}