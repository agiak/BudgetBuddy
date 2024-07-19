package com.example.features.quide.impl.di

import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.features.quide.impl.domain.GuideRepository
import com.example.features.quide.impl.domain.GuideRepositoryImpl
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