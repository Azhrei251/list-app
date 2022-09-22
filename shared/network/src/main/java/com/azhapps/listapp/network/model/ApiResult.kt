package com.azhapps.listapp.network.model

data class ApiResult<T>(
    val success: Boolean,
    val data: T?,
    val error: BackendError? = null,
)