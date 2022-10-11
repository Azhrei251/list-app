package com.azhapps.listapp.common.uc

import com.azhapps.listapp.common.data.CommonRemoteDataSource
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(
    private val remoteDataSource: CommonRemoteDataSource
) {
    suspend operator fun invoke() = callApi {
        remoteDataSource.getGroups()
    }
}