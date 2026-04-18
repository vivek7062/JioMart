package com.example.jiomartclone.data.repositoryimplementation

import CategoriesResponse
import com.example.jiomartclone.core.base.BaseRepository
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.core.dispatcher.DispatcherProvider
import com.example.jiomartclone.data.mapper.electronics.toDomain
import com.example.jiomartclone.data.mapper.groceries.toDomain
import com.example.jiomartclone.data.mapper.lowprice.toDomain
import com.example.jiomartclone.data.mapper.offertab.toDomain
import com.example.jiomartclone.data.mapper.toDomain
import com.example.jiomartclone.data.remote.api.CategoryService
import com.example.jiomartclone.domain.model.Category
import com.example.jiomartclone.domain.model.HomeHeaderCategory
import com.example.jiomartclone.domain.model.HomeLowPriceBanner
import com.example.jiomartclone.domain.model.electronics.Electronics
import com.example.jiomartclone.domain.model.groceries.GroceriesBanner
import com.example.jiomartclone.domain.model.groceries.GroceriesCategory
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct
import com.example.jiomartclone.domain.model.lowprice.ProductCategory
import com.example.jiomartclone.domain.model.offertab.OfferResponse
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import toDomain
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val dispatcherProvider: DispatcherProvider,
) : CategoryRepository, BaseRepository(dispatcherProvider) {

    override fun getTweetsByCategory(category: String): Flow<Resource<List<Category>>> {
        return safeApiCall(
            apiCall = { categoryService.getTweetsByCategory(category = category) },
            mapper = { it.map { dto -> dto.toDomain() } })
    }

    override fun getTweetsCategories(): Flow<Resource<List<String>>> {
        return safeApiCall(apiCall = { categoryService.getTweetsCategory() }, mapper = { it })
    }

    override fun getHomeHeaderCategory(): Flow<Resource<List<HomeHeaderCategory>>> {
        return safeApiCall(
            apiCall = { categoryService.getHomeHeaderCategory() },
            mapper = { it.map { dto -> dto.toDomain() } })
    }

    override fun getHomeLowPriceBanner(): Flow<Resource<List<HomeLowPriceBanner>>> {
        return safeApiCall(
            apiCall = { categoryService.getHomeLowPriceBanner() },
            mapper = { it.map { dto -> dto.toDomain() } })
    }

    override fun getHomeLowPriceCategoryWithProduct(): Flow<Resource<LowPriceCategoryWithProduct>> {
        return safeApiCall(
            apiCall = { categoryService.getHomeLowPriceCategoryWithProducts() },
            mapper = { it.toDomain() })
    }

    override fun getProductsByCategory(category: String): Flow<Resource<ProductCategory>> {
        return safeApiCall(
            apiCall = { categoryService.getProductsByCategory(category) },
            mapper = { it.toDomain() })
    }

    override fun getGroceriesCategory(): Flow<Resource<List<GroceriesCategory>>> {
        return safeApiCall(
            apiCall = { categoryService.getGroceriesCategories() },
            mapper = { it.map { dto -> dto.toDomain() } })
    }

    override fun getGroceriesBanner(): Flow<Resource<List<GroceriesBanner>>> {
        return safeApiCall(
            apiCall = { categoryService.getGroceriesBanner() },
            mapper = { it.map { dto -> dto.toDomain() } })
    }

    override fun getElectronicsData(): Flow<Resource<List<Electronics>>> {
        return safeApiCall(
            apiCall = { categoryService.getElectronicsData() },
            mapper = { it.map { dto -> dto.toDomain() } })
    }

    override fun getCategoriesTabData(): Flow<Resource<CategoriesResponse>> {
        return safeApiCall(
            apiCall = { categoryService.getCategoriesTabData() },
            mapper = { it.toDomain() })
    }

    override fun getOffersTabData(): Flow<Resource<OfferResponse>> {
        return safeApiCall(
            apiCall = { categoryService.getOffersTabData() },
            mapper = { it.toDomain() })
    }

}