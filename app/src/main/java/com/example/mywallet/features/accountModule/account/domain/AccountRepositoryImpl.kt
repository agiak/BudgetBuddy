package com.example.mywallet.features.accountModule.account.domain

import com.example.common.APP_DATE_FORMAT
import com.example.mywallet.core.data.common.TransactionType
import com.example.mywallet.core.domain.dispatchers.IDispatchers
import com.example.mywallet.features.accountModule.account.data.AccountDetails
import com.example.mywallet.features.accountModule.account.data.AccountStatics
import com.example.mywallet.storage.data.AccountDB
import com.example.mywallet.storage.data.TransactionDB
import com.example.mywallet.storage.domain.database.daos.AccountDao
import com.example.mywallet.storage.domain.database.daos.TransactionDao
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dispatchers: IDispatchers,
    private val accountsDao: AccountDao,
    private val transactionsDao: TransactionDao,
) : AccountRepository {

    override fun getAccountObservable(accountID: Long): Flow<AccountDB?> =
        accountsDao.getAccountObservable(accountID)

    override suspend fun fetchAccountDetails(accountID: Long): AccountDB? =
        withContext(dispatchers.backgroundThread()) {
            accountsDao.getAccount(accountID)
        }

    override suspend fun fetchTransactions(accountID: Long): List<TransactionDB> =
        withContext(dispatchers.backgroundThread()) {
            transactionsDao.getTransactionsForAccount(accountID)
        }

    override suspend fun updateAccount(accountInfo: AccountDetails) =
        withContext(dispatchers.backgroundThread()) {
            accountsDao.updateAccountDetails(accountDetails = accountInfo)
        }

    override suspend fun fetchAccountStatics(accountID: Long): AccountStatics? =
        withContext(dispatchers.backgroundThread()) { // Switch to the background thread context
            try {
                // Start async coroutine to fetch transactions for the account
                val transactionsDeferred =
                    async { transactionsDao.getTransactionsForAccount(accountID) }
                // Start async coroutine to fetch the current balance for the account
                val currentBalanceDeferred =
                    async { accountsDao.getAccount(accountID)?.balance ?: 0.0 }

                // Wait for both async operations to complete and retrieve their results
                val transactions = transactionsDeferred.await()
                val currentBalance = currentBalanceDeferred.await()

                // Filter transactions to separate income and expenses
                val incomeTransactions = transactions.filter { it.type == TransactionType.INCOME }
                val outcomeTransactions =
                    transactions.filter { it.type == TransactionType.OUTCOME }

                // Calculate the balance from the previous month
                val lastMonthBalance = transactions.filter {
                    !it.date.isDateAtTheCurrentMonth()
                }.sumOf {
                    if (it.type == TransactionType.MONEY_TRANSFER && it.accountFrom == accountID) -it.amount else it.amount
                }

                Timber.d("lastMonthBalance was $lastMonthBalance")

                // Calculate the change in balance from the last month to the current balance
                val lastMonthChange = currentBalance - lastMonthBalance
                // Calculate the percentage change in balance from the last month
                val lastMonthChangePercentage =
                    if (lastMonthBalance > 0) (100 * lastMonthChange) / lastMonthBalance else 0.0

                // Return the account statistics
                AccountStatics(
                    income = incomeTransactions.sumOf { it.amount },
                    outcome = outcomeTransactions.sumOf { it.amount },
                    numOfTransactions = transactions.size,
                    lastMonthChange = lastMonthChange,
                    lastMonthChangePercentage = lastMonthChangePercentage
                )
            } catch (ex: Exception) {
                Timber.e(ex) // Log the exception
                null // Return null in case of an exception
            }
        }

    private fun String.isDateAtTheCurrentMonth(): Boolean {
        val dateFormat = SimpleDateFormat(APP_DATE_FORMAT, Locale.getDefault())
        val inputDate: Date

        try {
            inputDate = dateFormat.parse(this) ?: return false
        } catch (e: Exception) {
            return false
        }

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        calendar.time = inputDate
        val inputMonth = calendar.get(Calendar.MONTH)
        val inputYear = calendar.get(Calendar.YEAR)

        return currentMonth == inputMonth && currentYear == inputYear
    }

    override suspend fun deleteAccount(accountID: Long) {
        withContext(dispatchers.backgroundThread()) {
            val account = accountsDao.getAccount(accountID)
            account?.let {
                val transactionsOfTheAccount = transactionsDao.getTransactionsForAccount(accountID)
                transactionsOfTheAccount.forEach { transactionDB ->
                    transactionsDao.deleteTransaction(transactionDB)
                }

                accountsDao.deleteAccount(account)
            } ?: throw Exception("Account didn't found for accountID: $accountID")
        }
    }
}
