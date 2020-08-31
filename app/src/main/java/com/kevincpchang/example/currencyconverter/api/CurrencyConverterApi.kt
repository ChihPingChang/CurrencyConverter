package com.kevincpchang.example.currencyconverter.api

import com.kevincpchang.example.currencyconverter.data.CurrencyRates
import com.kevincpchang.example.currencyconverter.data.CurrencyTypes
import retrofit2.http.GET

interface CurrencyConverterApi {
    // TODO : Dynamically insert access key by build config instead of hard coding.
    @GET("/api/list?access_key=e2552b9271e2eadc61830297b24db35d&format=1")
    suspend fun getCurrencyTypes(): CurrencyTypes

    @GET("/api/live?access_key=e2552b9271e2eadc61830297b24db35d&format=1")
    suspend fun getCurrencyRates(): CurrencyRates
}