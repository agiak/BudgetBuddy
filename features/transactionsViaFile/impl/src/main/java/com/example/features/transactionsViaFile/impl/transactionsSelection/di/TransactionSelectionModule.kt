package com.example.features.transactionsViaFile.impl.transactionsSelection.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.features.transactionsViaFile.impl.transactionsSelection.domain.TransactionsSelectionRepository
import com.example.features.transactionsViaFile.impl.transactionsSelection.domain.TransactionsSelectionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TransactionSelectionModule {

    @Singleton
    @Provides
    fun provideFileGuideRepository(
        dispatchers: IDispatchers,
        transactionDao: TransactionDao,
    ): TransactionsSelectionRepository = TransactionsSelectionRepositoryImpl(
        dispatchers = dispatchers,
        transactionsDao = transactionDao
    )
}
