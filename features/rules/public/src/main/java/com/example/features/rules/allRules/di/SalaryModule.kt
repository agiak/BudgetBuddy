package com.example.features.rules.allRules.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.RuleDao
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.features.rules.domain.SalaryRepository
import com.example.features.rules.domain.SalaryRepositoryImpl
import com.example.features.rules.domain.SalaryTransactionRepository
import com.example.features.rules.domain.SalaryTransactionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SalaryModule {

    @Singleton
    @Provides
    fun provideSalaryRepository(
        dispatchers: IDispatchers,
        ruleDao: RuleDao,
    ): SalaryRepository = SalaryRepositoryImpl(
        rulesDao = ruleDao,
        dispatchers = dispatchers
    )

    @Singleton
    @Provides
    fun provideSalaryTransactionRepository(
        dispatchersImpl: IDispatchers,
        dao: TransactionDao,
        accountDao: AccountDao,
    ): SalaryTransactionRepository = SalaryTransactionRepositoryImpl(
        dispatchers = dispatchersImpl,
        transactionDao = dao,
        accountsDao = accountDao
    )
}