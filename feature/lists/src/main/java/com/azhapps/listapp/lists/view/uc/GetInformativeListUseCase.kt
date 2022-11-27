package com.azhapps.listapp.lists.view.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class GetInformativeListUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource,
) {
    suspend operator fun invoke(listId: Int) = callApi {
        remoteDataSource.getListById(listId)
    }
}