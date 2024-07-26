package com.example.features.profile.impl.editProfile.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.features.profile.impl.editProfile.domain.EditProfileRepository
import com.example.features.profile.impl.editProfile.domain.EditProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EditProfileModule {

    @Singleton
    @Provides
    fun provideEditProfileRepository(
        dispatchers: IDispatchers,
        preferenceManager: PreferenceManager,
    ): EditProfileRepository = EditProfileRepositoryImpl(
        preferenceManager = preferenceManager,
        dispatchers = dispatchers
    )
}