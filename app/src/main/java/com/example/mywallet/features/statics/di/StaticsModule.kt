package com.example.mywallet.features.statics.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.statics.domain.StaticsRepository
import com.example.mywallet.features.statics.domain.StaticsRepositoryImpl
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StaticsModule {

    @Singleton
    @Provides
    fun provideStaticsRepository(
        dispatchers: IDispatchers,
        transactionDao: TransactionDao,
        accountsDao: AccountDao,
    ): StaticsRepository = StaticsRepositoryImpl(
        dispatchers = dispatchers,
        transactionsDao = transactionDao,
        accountsDao = accountsDao,
    )
}