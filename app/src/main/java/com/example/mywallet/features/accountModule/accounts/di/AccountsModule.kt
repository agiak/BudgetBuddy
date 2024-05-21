package com.example.mywallet.features.accountModule.accounts.di

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.accountModule.accounts.domain.AccountsRepository
import com.example.mywallet.features.accountModule.accounts.domain.AccountsRepositoryImpl
import com.example.mywallet.features.transactionsModule.transactions.domain.TransactionsRepository
import com.example.mywallet.features.transactionsModule.transactions.domain.TransactionsRepositoryImpl
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AccountsModule {

    @Singleton
    @Provides
    fun provideAccountsRepository(
        dao: AccountDao,
        dispatchers: IDispatchers,
    ): AccountsRepository = AccountsRepositoryImpl(
        accountsDao = dao,
        dispatchers = dispatchers
    )
}