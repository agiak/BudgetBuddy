package com.example.mywallet.storage.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mywallet.features.rulesModule.rules.data.Rule
import com.example.mywallet.storage.data.RuleDB

@Dao
interface RuleDao {

    @Query("SELECT * FROM rules WHERE rule = :rule ")
    suspend fun getRule(rule: Rule): RuleDB?

    @Query("SELECT * FROM rules")
    suspend fun getRules(): List<RuleDB>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRule(rule: RuleDB)

    @Update
    suspend fun updateRule(rule: RuleDB)

    @Query("DELETE FROM rules WHERE rule = :rule")
    suspend fun deleteRule(rule: Rule)
}