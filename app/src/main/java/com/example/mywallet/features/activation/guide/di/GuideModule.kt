package com.example.mywallet.features.activation.guide.di

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.accountModule.account.domain.AccountRepository
import com.example.mywallet.features.accountModule.account.domain.AccountRepositoryImpl
import com.example.mywallet.features.activation.guide.domain.GuideRepository
import com.example.mywallet.features.activation.guide.domain.GuideRepositoryImpl
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GuideModule {

    @Singleton
    @Provides
    fun provideGuideRepository(
        preferenceManager: PreferenceManager
    ): GuideRepository = GuideRepositoryImpl(
        preferenceManager = preferenceManager
    )
}