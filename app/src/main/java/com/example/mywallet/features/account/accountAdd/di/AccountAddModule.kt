package com.example.mywallet.features.account.accountAdd.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.account.accountAdd.domain.AddAccountRepository
import com.example.mywallet.features.account.accountAdd.domain.AddAccountRepositoryImpl
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AccountAddModule {

    @Singleton
    @Provides
    fun provideAddAccountRepository(
        dispatchersImpl: IDispatchers,
        dao: AccountDao,
        transactionsDao: TransactionDao,
    ): AddAccountRepository = AddAccountRepositoryImpl(
        dispatchers = dispatchersImpl,
        dao = dao,
        transactionsDao = transactionsDao,
    )
}