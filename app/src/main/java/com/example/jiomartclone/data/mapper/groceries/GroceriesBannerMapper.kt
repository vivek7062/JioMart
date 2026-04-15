package com.example.jiomartclone.data.mapper.groceries

import com.example.jiomartclone.data.remote.dto.groceries.GroceriesBannerDto
import com.example.jiomartclone.domain.model.groceries.GroceriesBanner

fun GroceriesBannerDto.toDomain() : GroceriesBanner{
    return GroceriesBanner(
        id = id,
        image = image,
        title = title,
        subtitle = subtitle,
        action_type = action_type,
        action_value = action_value,
        priority = priority,
        is_active = is_active
    )
}