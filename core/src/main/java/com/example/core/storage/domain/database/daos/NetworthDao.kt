package com.example.core.storage.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.storage.data.NetWorth
import com.example.core.storage.data.RuleDB

@Dao
interface NetworthDao {

    @Query("SELECT * FROM networth")
    suspend fun getNetWorths(): List<NetWorth>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAmount(netWorth: NetWorth)

    @Update
    suspend fun updateRule(rule: RuleDB)
}