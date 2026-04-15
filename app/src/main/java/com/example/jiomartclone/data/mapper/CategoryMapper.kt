package com.example.jiomartclone.data.mapper

import com.example.jiomartclone.data.remote.dto.category.CategoryDto
import com.example.jiomartclone.domain.model.Category

fun CategoryDto.toDomain() : Category{
    return Category(name = category, text= text)
}