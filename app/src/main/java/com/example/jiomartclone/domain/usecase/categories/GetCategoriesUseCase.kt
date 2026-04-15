package com.example.jiomartclone.domain.usecase.categories

import CategoriesResponse
import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    operator fun invoke(): Flow<Resource<CategoriesResponse>> =
        categoryRepository.getCategoriesTabData()
}