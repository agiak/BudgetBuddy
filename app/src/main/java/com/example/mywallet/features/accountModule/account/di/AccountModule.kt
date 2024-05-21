package com.example.mywallet.features.accountModule.account.di

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.accountModule.account.domain.AccountRepository
import com.example.mywallet.features.accountModule.account.domain.AccountRepositoryImpl
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao
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