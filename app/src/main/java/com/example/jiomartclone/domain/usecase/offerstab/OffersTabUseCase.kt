package com.example.jiomartclone.domain.usecase.offerstab

import com.example.jiomartclone.core.common.Resource
import com.example.jiomartclone.domain.model.offertab.OfferResponse
import com.example.jiomartclone.domain.repositoryinterface.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OffersTabUseCase @Inject constructor(private val categoryRepository: CategoryRepository){
    operator fun invoke() : Flow<Resource<OfferResponse>> = categoryRepository.getOffersTabData()
}