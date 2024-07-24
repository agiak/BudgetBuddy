package com.example.features.transactions.impl.transactions.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.features.transactions.impl.transactions.domain.TransactionsRepository
import com.example.features.transactions.impl.transactions.domain.TransactionsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TransactionsModule {

    @Singleton
    @Provides
    fun provideTransactionsRepository(
        dao: TransactionDao,
        dispatchers: IDispatchers,
    ): TransactionsRepository = TransactionsRepositoryImpl(
        dao = dao,
        dispatchers = dispatchers
    )
}