package com.example.features.rules.salary.domain

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.icu.util.Calendar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.core.data.common.TransactionType
import com.example.core.data.rule.Rule
import com.example.core.domain.ext.getCurrentDateFormatted
import com.example.core.domain.ext.getCurrentDateTime
import com.example.core.storage.data.RuleDB
import com.example.core.storage.data.TransactionDB
import com.example.core.storage.domain.database.daos.RuleDao
import com.agcoding.features.rules.R
import com.example.features.rules.domain.SalaryTransactionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val WORK_TAG = "Salary worker tag"

@HiltWorker
class SalaryWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: SalaryTransactionRepository,
    private val ruleDao: RuleDao,
) : CoroutineWorker(context, workerParams) {

    companion object {
        fun schedule(appContext: Context) {
            val workRequest = PeriodicWorkRequestBuilder<SalaryWorker>(1, TimeUnit.HOURS)
                .addTag(WORK_TAG)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(appContext)
                .enqueueUniquePeriodicWork(
                    "reminder_notification_work",
                    ExistingPeriodicWorkPolicy.UPDATE,
                    workRequest
                )
        }

        fun cancel(appContext: Context) {
            WorkManager.getInstance(appContext).cancelAllWorkByTag(WORK_TAG)
        }
    }

    override suspend fun doWork(): Result =
        runBlocking {
            Timber.d("do work was called")
            var result = Result.success()
            runCatching {
                if (!isFirstOfMonth()) {
                    Timber.d("This is not the first of the month")
                    //return@runCatching
                }

                val salaryRule: RuleDB? = ruleDao.getRule(Rule.Salary)
                val salaryTransaction = salaryRule?.toSalaryTransaction()
                if (salaryTransaction != null) {
                    Timber.d("inserts salary transaction $salaryTransaction")
                    repository.addTransaction(salaryTransaction, true)
                } else {
                    Timber.d("No salary rule detected")
                }
                salaryTransaction
            }.onSuccess { transactionDB ->
                transactionDB?.let { showNotification(it.description) }
                result = Result.success()
            }.onFailure {
                result = Result.failure()
            }
            result
        }

    @SuppressLint("MissingPermission")
    private fun showNotification(description: String) {

        if (NotificationManagerCompat.from(applicationContext).areNotificationsEnabled().not()) return

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            1,
            getNotification(description)
        )
    }

    private fun getNotification(description: String): Notification =
        NotificationCompat.Builder(
            applicationContext,
            applicationContext.resources.getStringArray(R.array.notification_channels_ids).first()
        )
            .setSmallIcon(com.agcoding.core.R.drawable.ic_logo)
            .setContentTitle(applicationContext.getString(R.string.notification_salary_title))
            .setContentText(description)
            .setColorized(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()


    private fun isFirstOfMonth(): Boolean =
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1

    private fun RuleDB.toSalaryTransaction(): TransactionDB =
        TransactionDB(
            amount = salary,
            type = TransactionType.INCOME,
            description = "Salary ${getCurrentDateTime()}",
            accountFrom = accountID,
            accountFromName = accountName,
            bankFromIcon = com.agcoding.core.R.drawable.ic_logo,
            date = getCurrentDateFormatted()
        )
}