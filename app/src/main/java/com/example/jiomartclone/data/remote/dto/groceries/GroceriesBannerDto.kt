package com.example.jiomartclone.data.remote.dto.groceries

data class GroceriesBannerDto(
    val action_type: String,
    val action_value: String,
    val id: Int,
    val image: String,
    val is_active: Boolean,
    val priority: Int,
    val subtitle: String,
    val title: String
)