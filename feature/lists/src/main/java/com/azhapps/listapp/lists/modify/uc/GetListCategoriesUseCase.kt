package com.azhapps.listapp.lists.modify.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class GetListCategoriesUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource
) {
    suspend operator fun invoke() = callApi {
        remoteDataSource.getListCategories()
    }
}