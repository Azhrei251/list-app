package com.azhapps.listapp.network

import com.azhapps.listapp.network.auth.AuthInterceptor
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

    const val DEFAULT_HOST = "http://localhost"

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(DEFAULT_HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideHttpClient(
        bearerAuthInterceptor: AuthInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(EnvironmentInterceptor())
        .addInterceptor(bearerAuthInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        })
        .build()

}
