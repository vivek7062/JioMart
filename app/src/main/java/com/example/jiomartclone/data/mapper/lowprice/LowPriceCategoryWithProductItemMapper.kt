package com.example.jiomartclone.data.mapper.lowprice

import com.example.jiomartclone.data.remote.dto.category.LowPriceCategoryWithProductDtoItem
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProductItem

fun LowPriceCategoryWithProductDtoItem.toDomain() : LowPriceCategoryWithProductItem{
    return LowPriceCategoryWithProductItem(
        id = categoryId,
        name = categoryName,
        products = products.map { it.toDomain() }
    )
}