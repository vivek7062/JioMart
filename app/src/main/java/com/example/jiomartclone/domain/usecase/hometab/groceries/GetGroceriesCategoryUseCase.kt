package com.example.jiomartclone.domain.usecase.hometab.groceries

import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.groceries.GroceriesCategory
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroceriesCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository)  {
    operator fun invoke() : Flow<Resource<List<GroceriesCategory>>> = categoryRepository.getGroceriesCategory()
}