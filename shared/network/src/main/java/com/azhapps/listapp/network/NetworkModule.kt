package com.azhapps.listapp.network

import android.accounts.AccountManager
import com.azhapps.listapp.network.auth.AuthInterceptor
import com.azhapps.listapp.network.auth.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl("http://localhost")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideHttpClient(
        tokenManager: TokenManager
    ) = OkHttpClient.Builder()
        .addInterceptor(EnvironmentInterceptor())
        .addInterceptor(AuthInterceptor(tokenManager))
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        })
        .build()

    @Provides
    fun provideTokenManager(
        accountManager: AccountManager
    ) = TokenManager(accountManager)
}
