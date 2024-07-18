package com.example.mywallet.features.account.accounts.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.account.accounts.domain.AccountsRepository
import com.example.mywallet.features.account.accounts.domain.AccountsRepositoryImpl
import com.example.core.storage.domain.database.daos.AccountDao
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
