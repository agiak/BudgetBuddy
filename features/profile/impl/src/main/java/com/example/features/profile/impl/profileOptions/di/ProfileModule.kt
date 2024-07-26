package com.example.features.profile.impl.profileOptions.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.features.profile.impl.profileOptions.domain.ProfileRepository
import com.example.features.profile.impl.profileOptions.domain.ProfileRepositoryImpl
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