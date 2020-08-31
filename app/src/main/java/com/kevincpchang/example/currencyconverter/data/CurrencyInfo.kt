package com.kevincpchang.example.currencyconverter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collect_table")
data class CurrencyInfo (
    @PrimaryKey
    @ColumnInfo(name = "source")
    var source: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "rate")
    var rate: Double = 1.0
)