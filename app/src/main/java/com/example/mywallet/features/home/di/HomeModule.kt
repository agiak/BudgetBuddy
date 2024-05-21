package com.example.mywallet.features.home.di

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.home.domain.HomeRepository
import com.example.mywallet.features.home.domain.HomeRepositoryImpl
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Singleton
    @Provides
    fun provideFavouritesRepository(
        accountsDao: AccountDao,
        transactionsDao: TransactionDao,
        dispatchers: IDispatchers,
    ): HomeRepository = HomeRepositoryImpl(
        accountDao = accountsDao,
        transactionsDao = transactionsDao,
        dispatchers = dispatchers
    )
}