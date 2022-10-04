package com.azhapps.listapp.lists.modify.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.network.Api
import javax.inject.Inject

class GetItemCategoriesUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource
) {
    suspend operator fun invoke() = Api.callApi {
        remoteDataSource.getItemCategories()
    }
}