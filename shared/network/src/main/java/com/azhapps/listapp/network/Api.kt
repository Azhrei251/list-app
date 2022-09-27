package com.azhapps.listapp.network

import android.util.Log
import com.azhapps.listapp.network.auth.RefreshManager
import com.azhapps.listapp.network.model.ApiResult
import com.azhapps.listapp.network.model.BackendError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

object Api {

    private val TAG = Api::class.java.simpleName
    private const val MAX_REFRESH_ATTEMPTS = 3
    private var refreshAttempts = 0

    suspend fun <T> callApi(
        apiCall: suspend () -> Response<T>
    ): ApiResult<T> = try {
        val apiResponse = apiCall()
        checkAndRefresh(apiResponse, apiCall)
    } catch (ioException: IOException) {
        ApiResult(false, null, buildErrorResult("Network issue", 400, ioException))
    } catch (throwable: Throwable) {
        ApiResult(false, null, buildErrorResult("Network issue", 400, throwable))
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun <T> checkAndRefresh(
        apiResult: Response<T>,
        apiCall: suspend () -> Response<T>
    ): ApiResult<T> = withContext(Dispatchers.IO) {
        val body = apiResult.body()
        if (apiResult.isSuccessful && body != null) {
            refreshAttempts = 0
            ApiResult(true, body)
        } else {
            if (apiResult.code() == 401) {
                if (refreshAttempts < MAX_REFRESH_ATTEMPTS) {
                    RefreshManager.refreshAuthToken(false)
                    checkAndRefresh(apiCall(), apiCall)
                } else if (refreshAttempts == MAX_REFRESH_ATTEMPTS) {
                    RefreshManager.refreshAuthToken(true)
                    checkAndRefresh(apiCall(), apiCall)
                } else {
                    val message = apiResult.errorBody()?.string() ?: "Failed max refresh attempts"
                    ApiResult(false, null, buildErrorResult(message, apiResult.code()))
                }
            } else {
                val message = apiResult.errorBody()?.string() ?: "Backend returned null error body"
                ApiResult(false, null, buildErrorResult(message, apiResult.code()))
            }
        }
    }

    private fun buildErrorResult(message: String, status: Int, throwable: Throwable? = null): BackendError {
        val error = BackendError(message, status, throwable)
        Log.e(TAG, message, error)
        return error
    }
}