package com.kevincpchang.example.currencyconverter.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun dbSetupModule() = module {
    single {
        Room.databaseBuilder(androidContext(), CurrencyDatabase::class.java, "CurrencyTable.db")
            .build()
    }
}

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}