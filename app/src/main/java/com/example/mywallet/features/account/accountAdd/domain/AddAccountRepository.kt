package com.example.mywallet.features.account.accountAdd.domain

import com.example.mywallet.storage.data.AccountDB

interface AddAccountRepository {

    suspend fun addAccount(accountDB: AccountDB)
}