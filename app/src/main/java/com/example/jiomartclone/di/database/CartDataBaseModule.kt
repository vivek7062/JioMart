package com.example.jiomartclone.di.database

import android.content.Context
import androidx.room.Room
import com.example.jiomartclone.data.local.auth.CartDao
import com.example.jiomartclone.data.local.auth.CartDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CartDataBaseModule {
    @Provides
    @Singleton
    fun provideCartDao(cartDataBase: CartDataBase): CartDao {
        return cartDataBase.cartDao()
    }

    @Provides
    @Singleton
    fun provideCartDatabase(@ApplicationContext context: Context): CartDataBase {
        return Room.databaseBuilder(context, CartDataBase::class.java, "cart")
            .fallbackToDestructiveMigration().build()
    }
}