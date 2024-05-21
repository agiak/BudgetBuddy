package com.example.mywallet.storage.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mywallet.core.data.bank.Bank

@Entity(tableName = "accounts")
data class AccountDB(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val bank: Bank,
    val balance: Double = 0.0,
    val createdDate: String,
    val colorId: Int = 0,
    val isSalaryAccount: Boolean = false,
) {
    constructor(
        name: String,
        bank: Bank,
        balance: Double,
        createdDate: String,
    ) : this(id = 0, name = name, bank = bank, balance = balance, createdDate = createdDate)
}
