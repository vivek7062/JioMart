package com.example.jiomartclone.domain.usecase.home

import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.Category
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTweetsByCategoryUseCase  @Inject constructor(val categoryRepository: CategoryRepository){
    operator fun invoke(category : String) : Flow<Resource<List<Category>>> {
        return categoryRepository.getTweetsByCategory("tweets[?(@.category==\"$category\")]")
    }
}