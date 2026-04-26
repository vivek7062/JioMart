package com.example.jiomartclone.data.local.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey val productId: Int,
    val name: String,
    val price: Int,
    val quantity: Int,
    val discount : Int,
    val image: String
)