package com.example.mywallet.features.transactionsModule.transactions.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.transactionsModule.transactions.domain.TransactionsRepository
import com.example.mywallet.features.transactionsModule.transactions.domain.TransactionsRepositoryImpl
import com.example.core.storage.domain.database.daos.TransactionDao
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