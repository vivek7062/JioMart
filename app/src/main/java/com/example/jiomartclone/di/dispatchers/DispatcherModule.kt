package com.example.jiomartclone.di.dispatchers

import com.example.jiomartclone.core.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Singleton
    @Provides
    fun provideDispatcher() : DispatcherProvider{
        return object : DispatcherProvider{
            override val main = Dispatchers.Main
            override val io = Dispatchers.IO
        }
    }
}