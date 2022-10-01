package com.azhapps.listapp.lists

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class UpdateInformativeListUseCase @Inject constructor(
    private val listsRemoteDataSource: ListsRemoteDataSource
) {
    suspend operator fun invoke(informativeList: InformativeList) =
        callApi {
            listsRemoteDataSource.updateList(informativeList.id, informativeList)
        }
}