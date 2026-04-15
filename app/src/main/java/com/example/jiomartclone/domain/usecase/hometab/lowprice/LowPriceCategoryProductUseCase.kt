package com.example.jiomartclone.domain.usecase.hometab.lowprice

import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.lowprice.LowPriceCategoryWithProduct
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LowPriceCategoryProductUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    operator fun invoke() : Flow<Resource<LowPriceCategoryWithProduct>> {
        return categoryRepository.getHomeLowPriceCategoryWithProduct()
    }
}