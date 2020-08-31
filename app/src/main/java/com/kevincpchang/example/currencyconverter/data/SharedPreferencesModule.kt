package com.kevincpchang.example.currencyconverter.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val preferencesModule = module {
    single { setupSharedPreferences(androidApplication()) }
}

private const val PREFERENCE_KEY = "com.kevincpchang.example.currencyconverter.preferences"

private fun setupSharedPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)