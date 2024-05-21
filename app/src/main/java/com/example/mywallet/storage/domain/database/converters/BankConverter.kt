package com.example.mywallet.storage.domain.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.mywallet.core.data.bank.Bank

@ProvidedTypeConverter
class BankConverter {
    @TypeConverter
    fun fromBankSupported(bankSupported: Bank): String {
        return bankSupported.name
    }

    @TypeConverter
    fun toBankSupported(bankSupportedName: String): Bank {
        return Bank.valueOf(bankSupportedName)
    }
}