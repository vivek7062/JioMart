package com.example.jiomartclone.domain.model.groceries

data class GroceriesCategory(
    val id: String,
    val image: String,
    val items: List<GroceriesCategoryItem>,
    val title: String
)