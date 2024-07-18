package com.example.core.storage.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.core.data.rule.Rule

@Entity(
    tableName = "rules",
    foreignKeys = [
        ForeignKey(
            entity = AccountDB::class,
            parentColumns = ["id"],
            childColumns = ["accountID"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index(value = ["accountID"])]
)
data class RuleDB(
    @PrimaryKey val rule: Rule,
    val salary: Double,
    val accountID: Long,
    val accountName: String,
)
