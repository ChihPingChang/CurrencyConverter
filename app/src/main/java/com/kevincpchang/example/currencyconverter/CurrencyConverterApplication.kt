package com.kevincpchang.example.currencyconverter

import android.app.Application
import com.kevincpchang.example.currencyconverter.api.networkModule
import com.kevincpchang.example.currencyconverter.data.currencyDataRepoModule
import com.kevincpchang.example.currencyconverter.data.currencyListRepoModule
import com.kevincpchang.example.currencyconverter.data.dbSetupModule
import com.kevincpchang.example.currencyconverter.data.preferencesModule
import com.kevincpchang.example.currencyconverter.ui.mainViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CurrencyConverterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    currencyListRepoModule,
                    networkModule,
                    mainViewModule,
                    dbSetupModule(),
                    currencyDataRepoModule,
                    preferencesModule
                )
            )
        }
    }
}