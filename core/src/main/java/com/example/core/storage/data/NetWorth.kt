package com.example.core.storage.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "networth")
data class NetWorth(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val amount: Double,
    val date: String,
)
