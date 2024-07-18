package com.example.core.storage.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.core.data.common.TransactionType

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = AccountDB::class,
            parentColumns = ["id"],
            childColumns = ["accountFrom"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AccountDB::class,
            parentColumns = ["id"],
            childColumns = ["accountTo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["accountFrom"]), Index(value = ["accountTo"])]
)
data class TransactionDB(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val amount: Double,
    val type: TransactionType,
    val accountFrom: Long,
    val accountFromName: String,
    val description: String = "",
    val bankFromIcon: Int,
    val accountTo: Long? = null,
    val accountToName: String? = null,
    val bankToIcon: Int? = null
) {

    // One account constructor
    constructor(
        date: String,
        amount: Double,
        type: TransactionType,
        accountFrom: Long,
        accountFromName: String,
        bankFromIcon: Int,
        description: String,
    ): this (
        id = 0,
        date = date,
        amount = amount,
        type = type,
        accountFrom = accountFrom,
        accountFromName = accountFromName,
        description = description,
        bankFromIcon = bankFromIcon,
        accountTo = null,
        accountToName = null,
        bankToIcon = null
    )

    // Internal transaction constructor
    constructor(
        date: String,
        amount: Double,
        type: TransactionType,
        accountFrom: Long,
        accountFromName: String,
        bankFromIcon: Int,
        accountTo: Long,
        accountToName: String,
        bankToIcon: Int?,
        description: String,
    ): this (
        id = 0,
        date = date,
        amount = amount,
        type = type,
        accountFrom = accountFrom,
        accountFromName = accountFromName,
        description = description,
        bankFromIcon = bankFromIcon,
        accountTo = accountTo,
        accountToName = accountToName,
        bankToIcon = bankToIcon
    )
}