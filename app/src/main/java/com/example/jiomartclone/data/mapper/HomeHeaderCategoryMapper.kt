package com.example.jiomartclone.data.mapper

import com.example.jiomartclone.data.remote.dto.category.HomeHeaderCategoryDto
import com.example.jiomartclone.domain.model.HomeHeaderCategory

fun HomeHeaderCategoryDto.toDomain() : HomeHeaderCategory{
    return HomeHeaderCategory(id = categoryId,icon = categoryIcon, title = categoryName)
}