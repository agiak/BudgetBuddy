package com.example.core.di

import com.example.core.domain.dispatchers.DispatchersImpl
import com.example.core.domain.dispatchers.IDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @Singleton
    fun providesCoroutineDispatcherIO(): IDispatchers =
        DispatchersImpl()
}
