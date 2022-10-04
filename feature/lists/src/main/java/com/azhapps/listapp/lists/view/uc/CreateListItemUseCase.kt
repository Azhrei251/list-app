package com.azhapps.listapp.lists.view.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.model.ListItem
import com.azhapps.listapp.lists.view.model.toCreateRequest
import com.azhapps.listapp.network.Api
import javax.inject.Inject

class CreateListItemUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource,
) {
    suspend operator fun invoke(item: ListItem, listId: Int) = Api.callApi {
        remoteDataSource.createListItem(item.toCreateRequest(listId))
    }
}