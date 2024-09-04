package com.example.core.storage.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.storage.data.AccountDB
import com.example.core.storage.data.NetWorth
import com.example.core.storage.data.RuleDB
import com.example.core.storage.data.TransactionDB
import com.example.core.storage.domain.database.converters.BankConverter
import com.example.core.storage.domain.database.converters.RuleTypeConverter
import com.example.core.storage.domain.database.converters.TransactionTypeConverter
import com.example.core.storage.domain.database.daos.AccountDao
import com.example.core.storage.domain.database.daos.RuleDao
import com.example.core.storage.domain.database.daos.TransactionDao

@Database(
    entities = [
        AccountDB::class,
        TransactionDB::class,
        RuleDB::class,
        NetWorth::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(BankConverter::class, TransactionTypeConverter::class, RuleTypeConverter::class)
abstract class WalletDatabase : RoomDatabase() {

    abstract fun transactionsDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun ruleDao(): RuleDao
}
