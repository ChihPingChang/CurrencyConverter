package com.kevincpchang.example.currencyconverter.data

class CurrencyTransfer(var data: MutableList<CurrencyInfo> = mutableListOf()) {
    fun getFullNameKeys(): MutableList<String> {
        val rateKeys: MutableList<String> = mutableListOf()
        for (info in data) {
            val nameKey = "(${info.source})${info.name}"
            rateKeys.add(nameKey)
        }

        return rateKeys
    }
}