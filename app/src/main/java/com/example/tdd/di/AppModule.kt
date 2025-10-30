package com.example.tdd.di

import com.example.tdd.model.area.AreaMapper
import com.example.tdd.model.common.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAreaMapper(): AreaMapper = AreaMapper()

    @Provides
    @Singleton
    fun provideErrorHandler(): ErrorHandler = ErrorHandler()
}