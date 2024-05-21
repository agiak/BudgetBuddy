package com.example.mywallet.features.transactionsModule.transactionAdd.di

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.transactionsModule.transactionAdd.domain.TransactionAddRepository
import com.example.mywallet.features.transactionsModule.transactionAdd.domain.TransactionAddRepositoryImpl
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TransactionAddModule {

    @Singleton
    @Provides
    fun provideTransactionAddRepository(
        dispatchersImpl: IDispatchers,
        dao: TransactionDao,
        accountDao: AccountDao,
    ): TransactionAddRepository = TransactionAddRepositoryImpl(
        dispatchers = dispatchersImpl,
        transactionDao = dao,
        accountsDao = accountDao
    )
}