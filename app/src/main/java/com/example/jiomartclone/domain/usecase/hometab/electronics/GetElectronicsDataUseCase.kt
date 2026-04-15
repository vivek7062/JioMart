package com.example.jiomartclone.domain.usecase.hometab.electronics

import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.electronics.Electronics
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetElectronicsDataUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    operator fun invoke() : Flow<Resource<List<Electronics>>> = categoryRepository.getElectronicsData()
}