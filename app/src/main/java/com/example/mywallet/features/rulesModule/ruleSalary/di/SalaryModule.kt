package com.example.mywallet.features.rulesModule.ruleSalary.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.mywallet.features.rulesModule.ruleSalary.domain.SalaryTransactionRepository
import com.example.mywallet.features.rulesModule.ruleSalary.domain.SalaryTransactionRepositoryImpl
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