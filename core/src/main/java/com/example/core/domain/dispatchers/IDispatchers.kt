package com.example.core.domain.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchers {
    fun mainThread(): CoroutineDispatcher
    fun backgroundThread() : CoroutineDispatcher
    fun defaultThread() : CoroutineDispatcher
}
