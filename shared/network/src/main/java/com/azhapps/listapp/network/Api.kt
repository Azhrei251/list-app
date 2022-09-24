package com.azhapps.listapp.network

import android.util.Log
import com.azhapps.listapp.network.model.ApiResult
import com.azhapps.listapp.network.model.BackendError
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object Api {

    private val TAG = Api::class.java.simpleName

    inline fun <T> callApi(
        apiCall: () -> Response<T>
    ): ApiResult<T> = try {
        val apiResult = apiCall()
        val body = apiResult.body()
        if (apiResult.isSuccessful && body != null) {
            ApiResult(true, body)
        } else {
            val message = apiResult.errorBody()?.string() ?: "Backend returned null error body"
            ApiResult(false, null, buildErrorResult(message, apiResult.code()))
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