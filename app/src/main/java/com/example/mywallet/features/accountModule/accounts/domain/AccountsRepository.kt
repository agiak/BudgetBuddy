package com.example.mywallet.features.accountModule.accounts.domain

import com.example.mywallet.features.accountModule.accounts.data.Account
import com.example.mywallet.storage.data.AccountDB
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    fun fetchAccounts(): Flow<List<AccountDB>>
}