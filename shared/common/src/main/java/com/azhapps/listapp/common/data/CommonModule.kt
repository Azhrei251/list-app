package com.azhapps.listapp.common.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    fun provideCommonRemoteDataSource(
        retrofit: Retrofit
    ): CommonRemoteDataSource = retrofit.create(CommonRemoteDataSource::class.java)
}