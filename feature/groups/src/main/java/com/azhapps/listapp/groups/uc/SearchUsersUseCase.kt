package com.azhapps.listapp.groups.uc

import com.azhapps.listapp.groups.data.GroupsRemoteDataSource
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val groupsRemoteDataSource: GroupsRemoteDataSource
) {
    suspend operator fun invoke(filter: String) = callApi {
        groupsRemoteDataSource.searchUser(filter)
    }
}