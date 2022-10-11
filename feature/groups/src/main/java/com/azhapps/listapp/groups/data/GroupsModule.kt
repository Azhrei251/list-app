package com.azhapps.listapp.groups.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object GroupsModule {

    @Provides
    fun provideCommonRemoteDataSource(
        retrofit: Retrofit
    ): GroupsRemoteDataSource = retrofit.create(GroupsRemoteDataSource::class.java)
}