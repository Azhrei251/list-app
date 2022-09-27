package com.azhapps.listapp.network.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideAuthRemoteDataSource(retrofit: Retrofit) = retrofit.create(AuthRemoteDataSource::class.java)
}