package com.example.features.quicklogin.impl.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.domain.user.UserRepository
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.features.quicklogin.impl.domain.QuickLoginRepository
import com.example.features.quicklogin.impl.domain.QuickLoginRepositoryImpl
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
        preferenceManager: PreferenceManager,
        userRepository: UserRepository,
    ): QuickLoginRepository = QuickLoginRepositoryImpl(
        accountDao = dao,
        dispatchers = dispatchers,
        preferenceManager = preferenceManager,
        userRepository = userRepository
    )
}