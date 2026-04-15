package com.example.jiomartclone.di.network

import android.annotation.SuppressLint
import com.example.jiomartclone.data.repositoryimplementation.CategoryRepositoryImpl
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CategoryRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCategoryRepository(imp : CategoryRepositoryImpl) : CategoryRepository
}