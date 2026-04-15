package com.example.jiomartclone.domain.repositoryinterface

import CategoriesResponse
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.Category
import com.example.jiomartclone.domain.model.HomeHeaderCategory
import com.example.jiomartclone.domain.model.HomeLowPriceBanner
import com.example.jiomartclone.domain.model.electronics.Electronics
import com.example.jiomartclone.domain.model.groceries.GroceriesBanner
import com.example.jiomartclone.domain.model.groceries.GroceriesCategory
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct
import com.example.jiomartclone.domain.model.lowprice.ProductCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getTweetsByCategory(category: String) : Flow<Resource<List<Category>>>

    fun getTweetsCategories() : Flow<Resource<List<String>>>

    fun getHomeHeaderCategory() : Flow<Resource<List<HomeHeaderCategory>>>

    fun getHomeLowPriceBanner() : Flow<Resource<List<HomeLowPriceBanner>>>

    fun getHomeLowPriceCategoryWithProduct() : Flow<Resource<LowPriceCategoryWithProduct>>

    fun getProductsByCategory(category : String) : Flow<Resource<ProductCategory>>

    fun getGroceriesCategory() : Flow<Resource<List<GroceriesCategory>>>

    fun getGroceriesBanner() : Flow<Resource<List<GroceriesBanner>>>

    fun getElectronicsData() : Flow<Resource<List<Electronics>>>

    fun getCategoriesTabData() : Flow<Resource<CategoriesResponse>>
}