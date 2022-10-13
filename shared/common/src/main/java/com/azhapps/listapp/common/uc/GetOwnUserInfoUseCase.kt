package com.azhapps.listapp.common.uc

import com.azhapps.listapp.common.data.CommonRemoteDataSource
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class GetOwnUserInfoUseCase @Inject constructor(
    private val commonRemoteDataSource: CommonRemoteDataSource
) {
    suspend operator fun invoke() = callApi {
        commonRemoteDataSource.userInfo()
    }
}