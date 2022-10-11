package com.azhapps.listapp.groups.uc

import com.azhapps.listapp.groups.data.GroupsRemoteDataSource
import com.azhapps.listapp.groups.model.CreateGroupRequest
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class RemoveUserFromGroupUseCase @Inject constructor(
    private val groupsRemoteDataSource: GroupsRemoteDataSource
) {
    suspend operator fun invoke(groupId: Int, userId: Int) = callApi {
        groupsRemoteDataSource.removeUserFromGroup(groupId, userId)
    }
}