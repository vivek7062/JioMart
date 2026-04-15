package com.example.jiomartclone.data.remote.dto.groceries

data class GroceriesCategoryDto(
    val id: String,
    val image: String,
    val items: List<GroceriesCategoryItemDto>,
    val title: String
)