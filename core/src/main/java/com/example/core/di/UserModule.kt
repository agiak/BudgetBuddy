package com.example.core.di

import com.example.core.domain.user.UserRepository
import com.example.core.domain.user.UserRepositoryImpl
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        preferenceManager: PreferenceManager
    ): UserRepository = UserRepositoryImpl(
        preferenceManager = preferenceManager
    )
}