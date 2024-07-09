package com.example.mywallet.features.activation.guide.di

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.features.activation.guide.domain.GuideRepository
import com.example.mywallet.features.activation.guide.domain.GuideRepositoryImpl
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