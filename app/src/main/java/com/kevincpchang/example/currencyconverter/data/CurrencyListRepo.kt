package com.kevincpchang.example.currencyconverter.data

import com.kevincpchang.example.currencyconverter.api.CurrencyConverterApi
import org.koin.dsl.module

val currencyListRepoModule = module {
    factory { CurrencyListRepo(get()) }
}

class CurrencyListRepo (private val currencyConverterApi: CurrencyConverterApi) {
    suspend fun getCurrencyTypes() = currencyConverterApi.getCurrencyTypes()
    suspend fun getCurrencyRates() = currencyConverterApi.getCurrencyRates()
}