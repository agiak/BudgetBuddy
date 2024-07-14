package com.example.mywallet.features.transactionsModule.fileTransactions.di

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.transactionsModule.fileTransactions.domain.FileGuideRepository
import com.example.mywallet.features.transactionsModule.fileTransactions.domain.FileGuideRepositoryImpl
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
