package com.example.jiomartclone.data.local.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Query("Select * From cart WHERE productId=:id")
    suspend fun getCartItem(id: Int) : CartItem?

    @Query("DELETE FROM cart WHERE productId = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM cart")
    suspend fun getCartItemsOnce(): List<CartItem>
}