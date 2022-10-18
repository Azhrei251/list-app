package com.azhapps.listapp.groups.uc

import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.groups.data.GroupsRemoteDataSource
import com.azhapps.listapp.groups.model.toModifyGroupRequest
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class UpdateGroupUseCase @Inject constructor(
    private val groupsRemoteDataSource: GroupsRemoteDataSource
) {
    suspend operator fun invoke(group: Group) = callApi {
        groupsRemoteDataSource.updateGroup(group.id, group.toModifyGroupRequest())
    }
}