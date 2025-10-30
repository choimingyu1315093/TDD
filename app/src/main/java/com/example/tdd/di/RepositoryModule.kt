package com.example.tdd.di

import com.example.tdd.model.area.AreaRepository
import com.example.tdd.model.area.AreaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAreaRepository(
        impl: AreaRepositoryImpl
    ): AreaRepository
}