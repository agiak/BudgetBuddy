package com.example.features.account.accountDetails.impl.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.features.account.accountDetails.impl.domain.AccountRepository
import com.example.features.account.accountDetails.impl.domain.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AccountModule {

    @Singleton
    @Provides
    fun provideAccountRepository(
        dispatchersImpl: IDispatchers,
        transactionDao: TransactionDao,
        accountDao: AccountDao,
    ): AccountRepository = AccountRepositoryImpl(
        dispatchers = dispatchersImpl,
        transactionsDao = transactionDao,
        accountsDao = accountDao
    )
}