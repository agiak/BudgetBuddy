package com.example.mywallet.features.activation.register.di

import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.activation.register.domain.RegisterRepository
import com.example.mywallet.features.activation.register.domain.RegisterRepositoryImpl
import com.example.core.storage.domain.database.daos.AccountDao
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
        preferenceManager: PreferenceManager,
        dao: AccountDao,
        dispatchers: IDispatchers,
    ): RegisterRepository = RegisterRepositoryImpl(
        accountsDao = dao,
        dispatchers = dispatchers,
        preferenceManager = preferenceManager
    )
}