package com.example.mywallet.core.data.bank

import com.example.mywallet.R

enum class Bank(
    val title: String,
    val drawableID: Int = R.drawable.ic_logo,
    val nickName: String = ""
) {

    EUROBANK("Eurobank", R.drawable.ic_eurobank, ""),
    ALPHABANK("AlphaBank", R.drawable.ic_alphabank, ""),
    REVOLUT("Revolut", R.drawable.ic_revolut, ""),
    NATIONAL_BANK("National Bank", R.drawable.ic_nbg, ""),
    CYRPTO("Crypto.com", R.drawable.ic_crypto_com, ""),
    PLUM("PLUM", R.drawable.ic_plum, ""),
    PIREAUS("Pireaus", R.drawable.ic_peiraus, ""),
    VIVA("Viva.com", R.drawable.ic_vivacom, ""),
    FREEDOM24("Freedom24", R.drawable.ic_freedom24,"");

    override fun toString(): String = title

}

fun String.toBank(): Bank? = Bank.entries.find { it.title == this }