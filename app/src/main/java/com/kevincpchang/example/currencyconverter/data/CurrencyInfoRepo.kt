package com.kevincpchang.example.currencyconverter.data

import org.koin.dsl.module

val currencyDataRepoModule = module {
    factory { CurrencyInfoRepo(get()) }
    factory { get<CurrencyDatabase>().currencyDao() }
}

class CurrencyInfoRepo(private val rateDao: CurrencyDao) {
    suspend fun findRate(source: String) = rateDao.findRate(source)
    suspend fun loadRateList(): List<CurrencyInfo> = rateDao.getAllList()
    suspend fun updateRate(saveRate: CurrencyInfo) = rateDao.updateRate(saveRate)
    suspend fun updateAllRate(saveList: List<CurrencyInfo>) = rateDao.updateAllRate(saveList)
}