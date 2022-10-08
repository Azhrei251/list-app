package com.azhapps.listapp.lists.modify.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.modify.model.CreateCategoryRequest
import com.azhapps.listapp.network.Api
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class CreateItemCategoryUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource,
) {
    suspend operator fun invoke(name: String) = callApi {
        remoteDataSource.createItemCategory(CreateCategoryRequest(name))
    }
}