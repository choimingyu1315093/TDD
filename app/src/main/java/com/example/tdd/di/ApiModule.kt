package com.example.tdd.di

import com.example.tdd.model.area.AreaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideAreaApiService(
        @Named("basicRetrofit") retrofit: Retrofit
    ): AreaApiService = retrofit.create(AreaApiService::class.java)
}