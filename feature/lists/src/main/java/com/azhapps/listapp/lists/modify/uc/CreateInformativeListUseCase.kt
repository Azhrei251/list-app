package com.azhapps.listapp.lists.modify.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.modify.model.toCreateRequest
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class CreateInformativeListUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource,
) {
    suspend operator fun invoke(informativeList: InformativeList) = callApi {
        remoteDataSource.createList(informativeList.toCreateRequest())
    }
}