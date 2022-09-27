package com.azhapps.listapp.lists.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ListsModule {

    @Provides
    fun provideLoginRemoteDataSource(
        retrofit: Retrofit
    ): ListsRemoteDataSource = retrofit.create(ListsRemoteDataSource::class.java)
}