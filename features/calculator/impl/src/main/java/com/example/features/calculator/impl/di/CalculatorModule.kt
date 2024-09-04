package com.example.features.calculator.impl.di

import com.example.core.domain.dispatchers.IDispatchers
import com.example.features.calculator.impl.domain.CalculatorRepository
import com.example.features.calculator.impl.domain.CalculatorRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CalculatorModule {

    @Singleton
    @Provides
    fun provideCalculatorRepository(
        dispatchers: IDispatchers
    ): CalculatorRepository = CalculatorRepositoryImpl(
        dispatchers = dispatchers
    )
}