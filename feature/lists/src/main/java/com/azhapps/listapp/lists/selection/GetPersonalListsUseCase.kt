package com.azhapps.listapp.lists.selection

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class GetPersonalListsUseCase @Inject constructor(
    private val listsRemoteDataSource: ListsRemoteDataSource
) {
    suspend operator fun invoke() = callApi {
        listsRemoteDataSource.getOwnLists()
    }
}