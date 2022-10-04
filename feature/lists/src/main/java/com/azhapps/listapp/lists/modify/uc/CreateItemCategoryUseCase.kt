package com.azhapps.listapp.lists.modify.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.modify.model.CreateCategoryRequest
import com.azhapps.listapp.network.Api
import javax.inject.Inject

class CreateItemCategoryUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource,
) {
    suspend operator fun invoke(name: String) = Api.callApi {
        remoteDataSource.createItemCategory(CreateCategoryRequest(name))
    }
}