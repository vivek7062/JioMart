package com.example.jiomartclone.domain.model.groceries

data class GroceriesBanner(
    val action_type: String,
    val action_value: String,
    val id: Int,
    val image: String,
    val is_active: Boolean,
    val priority: Int,
    val subtitle: String,
    val title: String
)