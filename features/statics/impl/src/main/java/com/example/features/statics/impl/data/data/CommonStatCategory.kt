package com.example.features.statics.impl.data.data

data class CommonStatCategory(val title: String, val results: List<CommonStatField>)

data class CommonStatField(val orderNumber: String, val description: String, val value: String)