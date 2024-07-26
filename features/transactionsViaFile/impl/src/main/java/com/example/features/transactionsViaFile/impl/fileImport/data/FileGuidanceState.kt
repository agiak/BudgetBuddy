package com.example.features.transactionsViaFile.impl.fileImport.data

sealed class FileState {
    data object Success : FileState()
    data object Idle : FileState()
    data class Saved(val transactionsAdded: Int) : FileState()
}