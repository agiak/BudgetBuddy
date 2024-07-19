package com.example.features.statics.impl.data.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.features.statics.impl.data.domain.StaticsRepository
import com.example.features.statics.impl.data.domain.StaticsRepositoryImpl
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