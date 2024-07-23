package com.example.features.accountAdd.impl.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.features.accountAdd.impl.domain.AddAccountRepository
import com.example.features.accountAdd.impl.domain.AddAccountRepositoryImpl
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