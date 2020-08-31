package com.kevincpchang.example.currencyconverter.data

import com.google.gson.annotations.SerializedName

data class CurrencyRates(
    @SerializedName("source") val source: String,
    @SerializedName("quotes") val quotes: Map<String, Double>
)