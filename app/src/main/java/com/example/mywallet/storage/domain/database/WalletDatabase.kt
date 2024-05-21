package com.example.mywallet.storage.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.RuleDB
import com.example.mywallet.storage.data.TransactionDB
import com.example.mywallet.storage.domain.database.converters.BankConverter
import com.example.mywallet.storage.domain.database.converters.RuleTypeConverter
import com.example.mywallet.storage.domain.database.converters.TransactionTypeConverter
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.RuleDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao

@Database(
    entities = [
        AccountDB::class,
        TransactionDB::class,
        RuleDB::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(BankConverter::class, TransactionTypeConverter::class, RuleTypeConverter::class)
abstract class WalletDatabase : RoomDatabase() {

    abstract fun transactionsDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun ruleDao(): RuleDao
}
