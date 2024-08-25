package com.example.feature.register.impl.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.feature.register.impl.domain.RegisterRepository
import com.example.feature.register.impl.domain.RegisterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RegisterModule {

    @Singleton
    @Provides
    fun provideRegisterRepository(
        dao: AccountDao,
        dispatchers: IDispatchers,
    ): RegisterRepository = RegisterRepositoryImpl(
        accountsDao = dao,
        dispatchers = dispatchers,
    )
}