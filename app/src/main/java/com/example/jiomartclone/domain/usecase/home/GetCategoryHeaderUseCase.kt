package com.example.jiomartclone.domain.usecase.home

import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.HomeHeaderCategory
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoryHeaderUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    operator fun invoke() : Flow<Resource<List<HomeHeaderCategory>>>{
        return categoryRepository.getHomeHeaderCategory()
    }
}