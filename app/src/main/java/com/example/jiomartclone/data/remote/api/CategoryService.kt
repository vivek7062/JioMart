package com.example.jiomartclone.data.remote.api

import CategoriesResponseDto
import android.R.attr.category
import androidx.room.Query
import com.example.jiomartclone.data.remote.dto.groceries.GroceriesBannerDto
import com.example.jiomartclone.data.remote.dto.category.CategoryDto
import com.example.jiomartclone.data.remote.dto.category.HomeHeaderCategoryDto
import com.example.jiomartclone.data.remote.dto.category.HomeLowPriceBannerDto
import com.example.jiomartclone.data.remote.dto.category.LowPriceCategoryWithProductDto
import com.example.jiomartclone.data.remote.dto.category.ProductCategoryDto
import com.example.jiomartclone.data.remote.dto.electronics.ElectronicsDto
import com.example.jiomartclone.data.remote.dto.groceries.GroceriesCategoryDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CategoryService {
    @GET("/v3/b/6963a26bd0ea881f406403fe?meta=false")
    suspend fun getTweetsByCategory(@Header("X-JSON-Path") category: String): List<CategoryDto>

    @GET("/v3/b/69bebb85aa77b81da906547b?meta=false")
    suspend fun getTweetsCategory(): List<String>

    @GET("/v3/b/69cb3ede856a682189e49f9b?meta=false")
    suspend fun getHomeHeaderCategory(): List<HomeHeaderCategoryDto>

    @GET("/v3/b/69cb3f8a36566621a86444b5?meta=false")
    suspend fun getHomeLowPriceBanner(): List<HomeLowPriceBannerDto>

    @GET("/v3/b/69cb4425856a682189e4b2a7?meta=false")
    suspend fun getHomeLowPriceCategoryWithProducts(): LowPriceCategoryWithProductDto

    @GET("/v3/b/{category}?meta=false")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductCategoryDto

    @GET("/v3/b/69d523ad36566621a889ec5e?meta=false")
    suspend fun getGroceriesCategories(): List<GroceriesCategoryDto>

    @GET("/v3/b/69d6823436566621a88fd711?meta=false")
    suspend fun getGroceriesBanner(): List<GroceriesBannerDto>

    @GET("/v3/b/69dbc53136566621a8a65505?meta=false")
    suspend fun getElectronicsData(): List<ElectronicsDto>

    @GET("/v3/b/69ddbafcaaba882197f7d797?meta=false")
    suspend fun getCategoriesTabData(): CategoriesResponseDto
}