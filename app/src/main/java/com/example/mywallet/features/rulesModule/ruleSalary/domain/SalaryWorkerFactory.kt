package com.example.mywallet.features.rulesModule.ruleSalary.domain

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.mywallet.features.transactionsModule.transactionAdd.domain.TransactionAddRepository
import com.example.core.storage.domain.database.daos.RuleDao
import javax.inject.Inject

class SalaryWorkerFactory @Inject constructor(
    private val repository: TransactionAddRepository, //TODO this needs to be changed
    private val ruleDao: RuleDao,
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =
        SalaryWorker(
            context = appContext,
            workerParams = workerParameters,
            repository = repository,
            ruleDao = ruleDao
        )
}