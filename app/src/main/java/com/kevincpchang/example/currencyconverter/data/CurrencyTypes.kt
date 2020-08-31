package com.kevincpchang.example.currencyconverter.data

import com.google.gson.annotations.SerializedName

data class CurrencyTypes(@SerializedName("currencies") val currencies: Map<String, String>)