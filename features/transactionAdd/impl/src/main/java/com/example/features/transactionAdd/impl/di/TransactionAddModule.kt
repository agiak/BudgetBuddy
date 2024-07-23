package com.example.features.transactionAdd.impl.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.features.transactionAdd.impl.domain.TransactionAddRepository
import com.example.features.transactionAdd.impl.domain.TransactionAddRepositoryImpl
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