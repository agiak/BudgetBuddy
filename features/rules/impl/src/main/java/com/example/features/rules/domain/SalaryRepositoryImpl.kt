package com.example.features.rules.domain

import com.example.core.data.rule.Rule
import com.example.core.domain.dispatchers.IDispatchers
import com.example.core.storage.data.RuleDB
import com.example.core.storage.domain.database.daos.RuleDao
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SalaryRepositoryImpl @Inject constructor(
    private val rulesDao: RuleDao,
    private val dispatchers: IDispatchers,
) : SalaryRepository {

    override suspend fun fetchCurrentSalaryRule(): RuleDB? =
        withContext(dispatchers.backgroundThread()) {
            rulesDao.getRule(rule = Rule.Salary)
        }

    override suspend fun deleteRule() =
        withContext(dispatchers.backgroundThread()) {
            rulesDao.deleteRule(rule = Rule.Salary)
        }

    override suspend fun enableRule(ruleDB: RuleDB) =
        withContext(dispatchers.backgroundThread()) {
            rulesDao.insertRule(ruleDB)
        }

}