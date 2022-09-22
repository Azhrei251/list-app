package com.azhapps.listapp.network.model

data class BackendError(
    val errorMessage: String,
    val httpStatus: Int,
    val rootCause: Throwable? = null
) : Throwable()