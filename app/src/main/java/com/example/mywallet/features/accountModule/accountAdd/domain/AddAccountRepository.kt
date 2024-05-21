package com.example.mywallet.features.accountModule.accountAdd.domain

import com.example.mywallet.storage.data.AccountDB

interface AddAccountRepository {

    suspend fun addAccount(accountDB: AccountDB)
}