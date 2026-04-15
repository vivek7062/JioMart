package com.example.jiomartclone.data.mapper.groceries

import com.example.jiomartclone.data.remote.dto.groceries.GroceriesCategoryItemDto
import com.example.jiomartclone.domain.model.groceries.GroceriesCategoryItem

fun GroceriesCategoryItemDto.toDomain() : GroceriesCategoryItem{
    return GroceriesCategoryItem(
        id = id,
        title = title,
        image = image
    )
}
