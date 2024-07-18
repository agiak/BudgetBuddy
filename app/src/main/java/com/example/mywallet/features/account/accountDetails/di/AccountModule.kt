package com.example.mywallet.features.account.accountDetails.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.account.accountDetails.domain.AccountRepository
import com.example.mywallet.features.account.accountDetails.domain.AccountRepositoryImpl
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
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