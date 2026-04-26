package com.example.jiomartclone.domain.model.offertab

data class FilterOption(
    val id: String,
    val label: String,
    val min: Int? = null,
    val max: Int? = null
)
