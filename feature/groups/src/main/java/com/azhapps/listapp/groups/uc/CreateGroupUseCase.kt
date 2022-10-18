package com.azhapps.listapp.groups.uc

import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.groups.data.GroupsRemoteDataSource
import com.azhapps.listapp.groups.model.ModifyGroupRequest
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val groupsRemoteDataSource: GroupsRemoteDataSource
) {
    suspend operator fun invoke(group: Group) = callApi {
        groupsRemoteDataSource.createGroup(ModifyGroupRequest(group.name, group.users.map {
            it.id
        }))
    }
}