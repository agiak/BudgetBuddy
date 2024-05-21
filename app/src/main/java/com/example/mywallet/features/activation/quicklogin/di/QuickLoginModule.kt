package com.example.mywallet.features.activation.quicklogin.di

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.accountModule.accounts.domain.AccountsRepository
import com.example.mywallet.features.accountModule.accounts.domain.AccountsRepositoryImpl
import com.example.mywallet.features.activation.quicklogin.domain.QuickLoginRepository
import com.example.mywallet.features.activation.quicklogin.domain.QuickLoginRepositoryImpl
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
class QuickLoginModule {

    @Singleton
    @Provides
    fun provideQuickLoginRepository(
        dao: AccountDao,
        dispatchers: IDispatchers,
        preferenceManager: PreferenceManager
    ): QuickLoginRepository = QuickLoginRepositoryImpl(
        accountDao = dao,
        dispatchers = dispatchers,
        preferenceManager = preferenceManager
    )
}