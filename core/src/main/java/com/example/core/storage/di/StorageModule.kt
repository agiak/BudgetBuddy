package com.example.core.storage.di

import android.content.Context
import androidx.room.Room
import com.example.core.storage.domain.WALLET_DATABASE_NAME
import com.example.core.storage.domain.database.WalletDatabase
import com.example.core.storage.domain.database.converters.BankConverter
import com.example.core.storage.domain.database.converters.RuleTypeConverter
import com.example.core.storage.domain.database.converters.TransactionTypeConverter
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.RuleDao
import com.example.core.storage.domain.database.daos.TransactionDao
import com.example.core.storage.domain.sharedprefs.PreferenceManager
import com.example.core.storage.domain.sharedprefs.PreferenceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager =
        PreferenceManagerImpl(context)

    @Singleton
    @Provides
    fun provideWalletDatabase(@ApplicationContext applicationContext: Context): WalletDatabase =
        Room.databaseBuilder(
            applicationContext,
            WalletDatabase::class.java, WALLET_DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .addTypeConverter(BankConverter())
            .addTypeConverter(TransactionTypeConverter())
            .addTypeConverter(RuleTypeConverter())
            .build()

    @Singleton
    @Provides
    fun provideAccountDao(db: WalletDatabase): AccountDao = db.accountDao()

    @Singleton
    @Provides
    fun provideTransactionsDao(db: WalletDatabase): TransactionDao = db.transactionsDao()

    @Singleton
    @Provides
    fun provideRuleDao(db: WalletDatabase): RuleDao = db.ruleDao()
}
