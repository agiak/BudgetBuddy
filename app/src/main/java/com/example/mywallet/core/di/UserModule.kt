package com.example.mywallet.core.di

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.domain.user.UserRepository
import com.example.mywallet.core.domain.user.UserRepositoryImpl
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