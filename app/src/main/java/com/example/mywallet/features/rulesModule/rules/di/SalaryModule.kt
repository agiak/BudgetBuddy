package com.example.mywallet.features.rulesModule.rules.di

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.profileModule.profile.domain.ProfileRepository
import com.example.mywallet.features.profileModule.profile.domain.ProfileRepositoryImpl
import com.example.mywallet.features.rulesModule.ruleSalary.domain.SalaryRepository
import com.example.mywallet.features.rulesModule.ruleSalary.domain.SalaryRepositoryImpl
import com.example.mywallet.storage.domain.database.daos.RuleDao
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
}