package com.azhapps.listapp.login.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    fun provideLoginRemoteDataSource(
        retrofit: Retrofit
    ): LoginRemoteDataSource = retrofit.create(LoginRemoteDataSource::class.java)
}