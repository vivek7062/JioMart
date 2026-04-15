package com.example.jiomartclone.data.mapper.groceries

import com.example.jiomartclone.data.remote.dto.groceries.GroceriesCategoryDto
import com.example.jiomartclone.domain.model.groceries.GroceriesCategory

fun GroceriesCategoryDto.toDomain(): GroceriesCategory{
    return GroceriesCategory(
        id = id,
        title = title,
        image = image,
        items = items.map { it.toDomain() }
    )
}
