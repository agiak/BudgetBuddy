package com.example.mywallet.features.profileModule.editProfile.di

import com.example.movierama.storage.domain.sharedpreferences.PreferenceManager
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.profileModule.editProfile.domain.EditProfileRepository
import com.example.mywallet.features.profileModule.editProfile.domain.EditProfileRepositoryImpl
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