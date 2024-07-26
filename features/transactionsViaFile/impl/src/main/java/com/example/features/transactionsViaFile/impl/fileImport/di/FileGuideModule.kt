package com.example.features.transactionsViaFile.impl.fileImport.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.features.transactionsViaFile.impl.fileImport.domain.FileGuideRepository
import com.example.features.transactionsViaFile.impl.fileImport.domain.FileGuideRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FileGuideModule {

    @Singleton
    @Provides
    fun provideFileGuideRepository(
        dispatchers: IDispatchers,
    ): FileGuideRepository = FileGuideRepositoryImpl(
        dispatchers = dispatchers,
    )
}
