package com.kevincpchang.example.currencyconverter.ui

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.*
import com.kevincpchang.example.currencyconverter.data.*
import org.koin.dsl.module
import java.util.*

val mainViewModule = module {
    factory { CurrencyViewModel(get(), get(), get()) }
}

class CurrencyViewModel(
    private val currencyListRepo: CurrencyListRepo,
    private val currencyDataRepo: CurrencyInfoRepo,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val trigger = MutableLiveData<Boolean>()
    private val checkPrice = MutableLiveData<Double>()
    private val targetRate = MutableLiveData<Double>()

    private val currencyTypes = trigger.switchMap { _ ->
        liveData {
            var result = CurrencyTransfer()
            try {
                val lastSave = sharedPreferences.getLong("logTime", -1)
                val queryLimit = 30 * 60 * 1000
                val logTime = Calendar.getInstance().timeInMillis

                if (lastSave + queryLimit > logTime) {
                    /* If the last time successfully fetching the data was less than 30 minutes,
                       get the data from Room.
                     */
                    result.data.addAll(currencyDataRepo.loadRateList())
                } else {
                    /* If the last time successfully fetching the data was more than 30 minutes,
                       fetch the data from CurrencyApi.
                     */
                    val currencyTypes = currencyListRepo.getCurrencyTypes()
                    val rateData = currencyListRepo.getCurrencyRates()
                    result = assembleCurrencyData(currencyTypes, rateData)
                }

            } catch (exception: Throwable) {
                exception.message?.let { "Error$it" }
            }
            emit(result)
        }
    }

    fun readyData(): LiveData<CurrencyTransfer> = currencyTypes
    fun getTargetPrice(): LiveData<Double> = checkPrice
    fun getStandRate(): LiveData<Double> = targetRate

    fun fetchData() {
        trigger.value = true
    }

    fun updatePrice(price: Double) {
        checkPrice.value = price
    }

    fun updateRate(rate: Double) {
        targetRate.value = rate
    }

    private suspend fun assembleCurrencyData(currencyTypes: CurrencyTypes, rateData: CurrencyRates): CurrencyTransfer {
        val returnData = CurrencyTransfer()
        val rates = rateData.quotes
        val currencies = currencyTypes.currencies
        val typeKeys: MutableList<String> = mutableListOf()
        typeKeys.addAll(currencies.keys.toList())
        val sources = rateData.source
        for (typeKey in typeKeys) {
            // full key = source currency + target currency
            val fullKey = "${sources}${typeKey}"
            /* Before creating and adding any CurrencyInfo object,
               check if we can find the key in the map of the rates.
             */
            if (rates.containsKey(fullKey)) {
                val fullName: String = currencies[typeKey] ?: ""
                // Add the CurrencyInfo object in the data list for the grid list.
                val currencyInfo =
                    CurrencyInfo(
                        source = typeKey,
                        name = fullName,
                        rate = rates[fullKey] ?: 1.0
                    )
                returnData.data.add(currencyInfo)
            }
        }
        // Save all of the assembled data in the Room.
        currencyDataRepo.updateAllRate(returnData.data)

        // Log the time in SharedPreferences.
        val logTime = Calendar.getInstance().time.time
        sharedPreferences.edit { putLong("logTime", logTime) }

        return returnData
    }
}