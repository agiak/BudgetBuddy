package com.example.mywallet.features.profileModule.profile.di

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.profileModule.profile.domain.ProfileRepository
import com.example.mywallet.features.profileModule.profile.domain.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Singleton
    @Provides
    fun provideProfileRepository(
        dispatchers: IDispatchers,
        preferenceManager: PreferenceManager,
    ): ProfileRepository = ProfileRepositoryImpl(
        preferenceManager = preferenceManager,
        dispatchers = dispatchers
    )
}