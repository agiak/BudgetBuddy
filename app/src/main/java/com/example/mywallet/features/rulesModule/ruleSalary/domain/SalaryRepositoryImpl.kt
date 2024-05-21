package com.example.mywallet.features.rulesModule.ruleSalary.domain

import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.rulesModule.rules.data.Rule
import com.example.mywallet.storage.data.RuleDB
import com.example.mywallet.storage.domain.database.daos.RuleDao
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