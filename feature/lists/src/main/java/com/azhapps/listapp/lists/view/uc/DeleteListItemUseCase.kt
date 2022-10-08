package com.azhapps.listapp.lists.view.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.model.ListItem
import com.azhapps.listapp.lists.view.model.toCreateRequest
import com.azhapps.listapp.network.Api
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class DeleteListItemUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource,
) {
    suspend operator fun invoke(itemId: Int) = callApi {
        remoteDataSource.deleteListItem(itemId)
    }
}