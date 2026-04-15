package com.example.jiomartclone.domain.usecase.categoryproduct

import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.lowprice.ProductCategory
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductByCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository){
    operator fun invoke(category: String) : Flow<Resource<ProductCategory>> {
        return categoryRepository.getProductsByCategory(category)
    }
}