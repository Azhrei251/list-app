package com.azhapps.listapp.network

import android.util.Log
import com.azhapps.listapp.network.auth.RefreshManager
import com.azhapps.listapp.network.model.ApiResult
import com.azhapps.listapp.network.model.BackendError
import retrofit2.Response
import java.io.IOException

object Api {

    private val TAG = Api::class.java.simpleName

    inline fun <T> callApi(
        apiCall: () -> Response<T>
    ): ApiResult<T> = try {
        val apiResponse = apiCall()
        val body = apiResponse.body()
        if (apiResponse.isSuccessful && body != null) {
            ApiResult(true, body)
        } else {
            val message = apiResponse.message() ?: apiResponse.errorBody()?.string() ?: "Backend returned null error body"
            ApiResult(false, null, buildErrorResult(message, apiResponse.code()))
        }
    } catch (ioException: IOException) {
        ApiResult(false, null, buildErrorResult("Network issue", 400, ioException))
    } catch (throwable: Throwable) {
        ApiResult(false, null, buildErrorResult("Network issue", 400, throwable))
    }

    fun buildErrorResult(message: String, status: Int, throwable: Throwable? = null): BackendError {
        val error = BackendError(message, status, throwable)
        Log.e(TAG, message, error)
        return error
    }
}