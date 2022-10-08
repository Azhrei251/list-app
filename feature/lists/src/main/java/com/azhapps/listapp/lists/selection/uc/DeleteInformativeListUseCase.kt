package com.azhapps.listapp.lists.selection.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.selection.model.toCreateRequest
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class DeleteInformativeListUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource,
) {
    suspend operator fun invoke(listId: Int) = callApi {
        remoteDataSource.deleteList(listId)
    }
}