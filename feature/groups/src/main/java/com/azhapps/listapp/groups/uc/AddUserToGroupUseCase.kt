package com.azhapps.listapp.groups.uc

import com.azhapps.listapp.groups.data.GroupsRemoteDataSource
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class AddUserToGroupUseCase @Inject constructor(
    private val groupsRemoteDataSource: GroupsRemoteDataSource
) {
    suspend operator fun invoke(groupId: Int, userId: Int) = callApi {
        groupsRemoteDataSource.addUserToGroup(groupId, userId)
    }
}