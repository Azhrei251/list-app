package com.azhapps.listapp.lists.view.uc

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.model.ListItem
import com.azhapps.listapp.lists.view.model.toModifyRequest
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class UpdateListItemUseCase @Inject constructor(
    private val listsRemoteDataSource: ListsRemoteDataSource
) {
    suspend operator fun invoke(listItem: ListItem, listId: Int) = callApi {
        listsRemoteDataSource.updateItem(listItem.id, listItem.toModifyRequest(listId))
    }
}