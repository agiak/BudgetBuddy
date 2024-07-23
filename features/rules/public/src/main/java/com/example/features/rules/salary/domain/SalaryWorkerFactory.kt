package com.example.features.rules.salary.domain

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.core.storage.domain.database.daos.RuleDao
import com.example.features.rules.domain.SalaryTransactionRepository
import javax.inject.Inject

class SalaryWorkerFactory @Inject constructor(
    private val repository: SalaryTransactionRepository,
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