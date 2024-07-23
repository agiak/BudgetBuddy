package com.example.features.accounts.impl.accounts.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.features.accounts.impl.accounts.domain.AccountsRepository
import com.example.features.accounts.impl.accounts.domain.AccountsRepositoryImpl
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
