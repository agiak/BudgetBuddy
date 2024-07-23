package com.example.features.accountAdd.impl.domain

import com.example.core.storage.data.AccountDB

interface AddAccountRepository {

    suspend fun addAccount(accountDB: AccountDB)
}