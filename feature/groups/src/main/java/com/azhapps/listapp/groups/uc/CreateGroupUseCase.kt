package com.azhapps.listapp.groups.uc

import com.azhapps.listapp.groups.data.GroupsRemoteDataSource
import com.azhapps.listapp.groups.model.CreateGroupRequest
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val groupsRemoteDataSource: GroupsRemoteDataSource
) {
    suspend operator fun invoke(groupName: String) = callApi {
        groupsRemoteDataSource.createGroup(CreateGroupRequest(groupName))
    }
}