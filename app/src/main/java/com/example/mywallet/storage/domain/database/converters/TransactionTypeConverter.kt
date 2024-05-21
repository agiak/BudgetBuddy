package com.example.mywallet.storage.domain.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.mywallet.core.data.common.TransactionType

@ProvidedTypeConverter
class TransactionTypeConverter {
    @TypeConverter
    fun fromBankSupported(transactionType: TransactionType): String {
        return transactionType.name
    }

    @TypeConverter
    fun toBankSupported(transactionType: String): TransactionType {
        return TransactionType.valueOf(transactionType)
    }
}